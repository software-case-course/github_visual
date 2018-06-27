<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2018/4/12
  Time: 16:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>
        Github Visualization - Repository Rank
    </title>

    <style>
        #div1{
        background:-webkit-linear-gradient(top,pink,white)
    }
        .map_path {
            stroke: black;
            stroke-width: 1px;
        }

        .grid_path{
            stroke: gray;
            stroke-width: 1px;
            fill:none;
            opacity: 1;
        }
    </style>

    <script src="../lib/jquery/jquery-3.2.1.min.js"></script>
    <script src="https://unpkg.com/popper.js/dist/umd/popper.min.js"></script>

    <script src="../js/bootstrap.min.js"></script>
    <script src="../lib/echarts.js"></script>
    <script src="../js/bootstrap-table.js"></script>
    <script src="../js/bootstrap-table-filter-control.js"></script>
    <link href="../css/bootstrap.min.css.map">
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="stylesheet" href="../assets/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="../css/bootstrap-table.css">
    <link rel="stylesheet" href="../css/bootstrap-table-filter-control.css">

    <%--<link rel="stylesheet" href="../lib/bootstrap-table.min.css">--%>
    <%--<script type="text/javascript" src="../lib/bootstrap-table.min.js"></script>--%>
    <%--<script type="text/javascript" src="../lib/bootstrap-table-locale-all.min.js"></script>--%>

</head>

<body>

<script src="../js/d3.min.js"></script>

<%--<body onload="loadData('stars')">--%>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="../index.html">Github Visualization</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse"
            data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item active">
                <a class="nav-link" href="../index.html#">Home<span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link active" href="rank.jsp">Repository Rank</a>
            <li class="nav-item">
                <a class="nav-link" href="trend.jsp">Language Trend</a>
            </li>
        </ul>
    </div>
</nav>

<div class="jumbotron jumbotron-fluid" id="div1">
        <div class="container" >
            <h1 class="display-4">仓库排行</h1>
          <p class="lead">
              此页面统计了Github上各个项目，挑选出了比较热门的仓库并将它们的仓库数量以及使用的语言、地区情况进行了图表展示，您还可以通过点击这些热门语言查看这些语言中比较热门的一些仓库
          </p>
        </div>
      </div>

<div class="row" style="margin: 30px">
    <div class="col-md-6">
        <div id="globe"></div>
    </div>
    <div class="col-md-6">
        <form>
            <div class="form-group row">
                <label for="FormControlOrderSelect" class="col-md-6">Order</label>
                <select class="form-control col-md-6" id="FormControlOrderSelect" onchange="selectOnchang(this)">
                    <option value="stars">Stars</option>
                    <option value="forks">Forks</option>
                </select>
            </div>
        </form>

        <table id="table" data-filter-control="true" data-filter-show-clear="true">
            <thead>
            <tr>
                <th data-field="repo_name" data-sortable="true">仓库名称</th>
                <th data-field="stars" data-sortable="true">Stars</th>
                <th data-field="forks" data-sortable="true">Forks</th>
                <th data-field="language" data-filter-control="select">语言</th>
                <th class="filter-control" data-field="location" data-filter-control="input">位置</th>
            </tr>
            </thead>
        </table>
    </div>
</div>


<script>
    var width = 500;
    var height = 500;
    var speed = 0.02;
    var startTime = Date.now();
    var currentTime = Date.now();

    var svg = d3.select('#globe').append("svg")
        .attr("width", width)
        .attr("height", height);

    $("svg").css({top: 110, left: 50, position: 'relative'});

    var projection = d3.geo.orthographic()
        .scale(230);

    var graticule = d3.geo.graticule();

    var path = d3.geo.path()
        .projection(projection);

    var color = d3.scale.category20();

    svg.append("text")
        .attr("id", "loading")
        .attr("x", width / 2)
        .attr("y", height / 2)
        .text("Now Loading...");


    d3.json("../js/world_605kb.json", function (error, root) {
        if (error)
            return console.error(error);
        console.log(root);

        var grid = graticule();

        console.log(grid);

        var map = svg.append("g")
        <%--        .attr("transform", "translate(-230 -20"); --%>
            .attr("transform", "translate(" + -230 + "," + -20 + ")");

        map.append("path")
            .datum(grid)
            .attr("id", "grid_id")
            .attr("class", "grid_path")
            .attr("d", path);

        map.selectAll(".map_path")
            .data(root.features)
            .enter()
            .append("path")
            .attr("class", "map_path")
            .attr("fill", function (d, i) {
                return color(i);
            })
            .attr("d", path)
            .on("mouseover", function (d, i) {
                d3.select(this)
                    .attr("fill", "yellow");
            })
            .on("mouseout", function (d, i) {
                d3.select(this)
                    .attr("fill", color(i));
            });

        svg.select("#loading")
            .attr("opacity", 0);

        d3.timer(function () {
            currentTime = Date.now();

            projection.rotate([speed * (currentTime - startTime), -15]).clipAngle(90);

            map.select("#grid_id")
                .attr("d", path);

            map.selectAll(".map_path")
                .attr("d", path);

        });
    });

</script>

<script>
    var $table = $('#table');

    $(function () {
        $table.bootstrapTable({
            url: '../test/getHighlyRatedRepositories_stars.json',         //请求后台的URL（*）
            contentType: "application/x-www-form-urlencoded",
            method: 'get',
            dataType: 'json',
            toggle: 'table',
            height: '600',
            cache: false,
            toolbar: '#toolbar',
            showRefresh: true,
            showToggle: true,
            showColumns: true,
            showExport: true,
            queryParams: queryParams,
            responseHandler: responseHandler,
            // classes: "table-no-bordered",  //表格无边框
            pagination: true, //是否显示分页（*）
            sortable: false, //是否启用排序
            sortOrder: "asc", //排序方式
            sideSort: "client",
            sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [5, 10, 20, 50, 100], //可供选择的每页的行数（*）
            search: false, //是否显示表格搜索
            strictSearch: true,
            minimumCountColumns: 2, //最少允许的列数
            clickToSelect: true, //是否启用点击选中行
            onClickRow: function (row) {
                // console.log(row);
                var repo = row.repo_name;
                temp = repo.split('/');
                repo = temp[1];
                // console.log(repo)
                window.location.href = "repo.jsp?owner="+ row.owner + "&repo=" + repo;
            }
        });
    });

    function queryParams() {
        /* var params = {};
         $('#toolbar').find('input[name]').each(function () {
             params[$(this).attr('name')] = $(this).val();
         });*/
        // alert($("#FormControlOrderSelect option:selected").val());
        var params = {};
        params.orderBy = $("#FormControlOrderSelect option:selected").val();
        console.log(params);
        return params;
    }

    function responseHandler(res) {
        console.log(res);
        return res.data.datas;
        // return res.data.datas.rows;
    }

    function selectOnchang(obj) {
        var value = obj.options[obj.selectedIndex].value;
        // loadData(value);

        // $table.bootstrapTable('destroy').bootstrapTable($.extend({
        //     queryParams: queryParams
        // }));

        if (value === 'stars') {
            $table.bootstrapTable('refresh', {
                url: '../test/getHighlyRatedRepositories_stars.json'
            });
        } else if (value === 'forks') {
            $table.bootstrapTable('refresh', {
                url: '../test/getHighlyRatedRepositories_forks.json'
            });
        }

        // alert(value);
    }


    /*  $('#tb_rank').bootstrapTable({
          url: '../test/orderByStars.json',         //请求后台的URL（*）
          method: 'get',                      //请求方式（*）
          locale:'zh-CN',//中文支持
          toolbar: '#toolbar',                //工具按钮用哪个容器
          showFooter: true,  //表格脚部，用来计算统计总和
          classes: "table-no-bordered",  //表格无边框
          striped : true, //是否显示行间隔色
          cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
          pagination : true, //是否显示分页（*）
          sortable : true, //是否启用排序
          sortOrder : "asc", //排序方式
          queryParams: function(param) {
              return param;
          },//传递参数（*）
          sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
          pageNumber: 1,                       //初始化加载第一页，默认第一页
          pageSize: 2,                       //每页的记录行数（*）
          pageList : [2,3,4], //可供选择的每页的行数（*）
          search : false, //是否显示表格搜索
          strictSearch : true,
          showColumns : false, //是否显示所有的列
          showRefresh : false, //是否显示刷新按钮
          minimumCountColumns : 2, //最少允许的列数
          clickToSelect : true, //是否启用点击选中行
          uniqueId : "fullName", //每一行的唯一标识，一般为主键列
          showToggle : true, //是否显示详细视图和列表视图的切换按钮
          cardView : false, //是否显示详细视图
          detailView : true, //是否显示父子表
          columns: [{
              checkbox: false,
              visible: false
          },{
              field: 'fullName',
              title: '仓库名称',
          }, {
              field: 'num',
              title: '数量',
              sortable: true,
          }, {
              field: 'language',
              title: '使用语言',
              filterControl: 'select',
          }, {
              field: 'region',
              title: '位置',
              filterControl: 'input',
              filterShowClear: 'true'
          }]
      });*/
    /*  function loadData(value) {
          $.ajax({
              type: 'GET',
              dataType: "json",
              url: "../test/orderByStars.json",
              data: {
                  orderBy: value
              },
              success: function(result) {
                  // alert("查询成功")
                  var str = "";
                  var data = result.data;

                  var tbody = window.document.getElementById("tbody-result");

                  for (i in data) {
                      str += "<tr>" +
                          "<td>" + data[i].fullName + "</td>" +
                          "<td>" + data[i].num + "</td>" +
                          "<td>" + data[i].language + "</td>" +
                          "<td>" + data[i].region + "</td>" +
                          "</tr>";
                  }
                  tbody.innerHTML = str;
              },
              error: function() {
                  alert("查询失败");
              }
          })
      }

      function selectOrderOnchang(obj) {
          var value = obj.options[obj.selectedIndex].value;
          loadData(value);
      }

      function selectLanguageOnchang(obj, result) {
          var value = obj.options[obj.selectedIndex].value;
      }*/



</script>

</body>
</html>
