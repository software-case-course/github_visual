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
    <meta charset="UTF-8">
    <title>
        Github Visualization - Language Trend
    </title>

    
    <script src="../lib/jquery/jquery-3.2.1.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts.min.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts-gl/echarts-gl.min.js"></script>
    <script type="text/javascript"
            src="http://echarts.baidu.com/gallery/vendors/echarts/extension/dataTool.min.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/china.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/world.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ZUONbpqGBsYGXNIYHicvbAbM"></script>
    <script type="text/javascript"
            src="http://echarts.baidu.com/gallery/vendors/echarts/extension/bmap.min.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/simplex.js"></script>
    <script src="../lib/echarts.js"></script>
    <link href="../css/bootstrap.min.css.map">
    <link rel="stylesheet" href="../css/bootstrap.min.css">

    <style>
        #div1{
        background:-webkit-linear-gradient(top,rgb(136, 123, 209),white)
    }

    </style>

</head>
<body onload="loadDataByYear()">

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="../index.html">Github Visualization</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse"
            data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="../index.html#">Home<span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="rank.jsp">Repository Rank</a>
            <li class="nav-item active">
                <a class="nav-link" href="trend.jsp">Language Trend</a>
            </li>
        </ul>
    </div>
</nav>

<div class="jumbotron jumbotron-fluid" id="div1">
          <div class="container" >
            <h1 class="display-4">语言趋势</h1>
            <p class="lead">
              在这里，我们为您展示了不同时期内Github上各语言的统计数据，来为您描绘各种语言的发展趋势。
            </p>
          </div>
        </div>

    <form style="padding: 20px">
        <div class="form-group row">
            <label for="FormControlSelect" class="col-md-2 offset-3">Options</label>
            <select class="form-control col-md-4" id="FormControlSelect" onchange="selectOnchang(this)">
                <option value="CheckLanguageUseByYear">By Year</option>
                <option value="CheckLanguageUseByMonth">By Month</option>
            </select>
        </div>
    </form>

<%--<div class="container" style="height:800px;width:800px" id="lineDiv"></div>--%>
<div class="container" style="height: 700px; width:1200px" id="lineDiv"></div>


    <script>
        
        var myChart=echarts.init(document.getElementById("lineDiv"));
        function selectOnchang(obj){
            var value = obj.options[obj.selectedIndex].value;
            if(value=="CheckLanguageUseByYear"){
                loadDataByYear();
            }
            else loadDataByMonth();
        }

        function loadDataByYear(){
            
            myChart.showLoading();
            $.get("../test/CheckLanguageUseByYear.json").done(function(res){
            var languages=[];
            var datas=[];
            var data=[];
            var xAxis=[];
            var Series=[];
            for(var i=0;i<10;i++){
                xAxis.push(2008+i+"年");
            }
                myChart.hideLoading();
                var record=res.data.data;
                var len=record.length;
                var pos='';
                for(var i=0;i<len;i++){
                    var item=record[i];
                    var flag=languages.indexOf(item.language);
                    if(flag==-1){
                        if(pos!=''){
                            datas[pos]=data;
                            data=[];
                        }
                        languages.push(item.language);
                        pos=item.language;
                    }
                    data.push(item.data.number);
                }
                for(var j=0;j<languages.length;j++){
                  var temp={
                    name:languages[j],
                    type:'line',
                    data:datas[languages[j]]
                    }
                  Series.push(temp);
                  }
          
            myChart.setOption({
                title:{
                    text:"各语言变化趋势"
                },
                tooltip:{
                    trigger:'axis'
                },
                legend:{
                    data:languages
                },
                xAxis:{
                    type:'category',
                    data:xAxis,
                    name:'年份'
                },
                yAxis:{
                    name:"数量",
                },
                series:Series
            })
            })

            myChart.on("click",function(params){
                loadDataByMonth();
            })
        }


        function loadDataByMonth(){
            myChart.showLoading();
            $.get("../test/CheckLanguageUseByMonth.json").done(function(res){
            myChart.hideLoading();
            var languages=[];
            var datas=[];
            var data=[];
            var xAxis=[];
            var Series=[];
            for(var i=0;i<13;i++){
                xAxis.push(i+"月");
            }
            var record=res.data.data;
            var len=record.length;
            var pos='';
            for(var i=0;i<len;i++){
                    var item=record[i];
                    var flag=languages.indexOf(item.language);
                    if(flag==-1){
                        if(pos!=''){
                            datas[pos]=data;
                            data=[];
                        }
                        languages.push(item.language);
                        pos=item.language;
                    }
                    data.push(item.data.number);
                }
                for(var j=0;j<languages.length;j++){
                  var temp={
                    name:languages[j],
                    type:'line',
                    data:datas[languages[j]]
                    }
                  Series.push(temp);
                  }
                myChart.setOption({
                title:{
                    text:"各语言变化趋势"
                },
                tooltip:{
                    trigger:'axis'
                },
                legend:{
                    data:languages
                },
                xAxis:{
                    type:'category',
                    data:xAxis,
                    name:'月份'
                },
                yAxis:{
                    name:"数量",
                },
                series:Series
            })
            })      

        }
        
        /*
        function selectOnchang(obj) {
            var value = obj.options[obj.selectedIndex].value;
            if (value === "CheckLanguageUseByYear") {
                xAxisData = [];
                datas = [];
                Series = [];
                for (var i = 0; i <= 10; i++) {
                    xAxisData.push(2008 + i);
                }
                myChart.setOption({
                    xAxis: {
                        data: xAxisData
                    }
                });
                loadDataByYear();

            } else if (value === "CheckLanguageUseByMonth") {
                xAxisData = [];
                datas = [];
                Series = [];
                for (var i = 1; i < 13; i++) {
                    xAxisData.push(i + "月");
                }
                myChart.setOption({
                    xAxis: {
                        data: xAxisData
                    }
                });
                loadDataByMonth();
            }
        }
        

        function loadDataByYear() {
            myChart.showLoading();
            $.get('../test/CheckLanguageUseByYear.json').done(function (res) {//Python js ruby c# are wrong!
                myChart.hideLoading();
               // console.log(res);
             //   console.log(res.data);
               // console.log(res.data.data);
                // console.log(result);
                var result = res.data.data;
                // console.log(temp);
                var len = result.length;
                var data = [];
                var languages = [];
                var lastpos = ''
                // var datas = [];
                for (var i = 0; i < len; i++) {
                    var item = result[i];

                    var isExist = languages.indexOf(item.language);
                    // console.log(item.language + '.isExist = ' + isExist);

                    if (isExist === -1) {
                        if (lastpos !== '')
                            datas[lastpos] = data;
                        languages.push(item.language);
                        data = [];
                        lastpos = item.language;
                    }

                    var temp = item.data;

                    data.push(temp.number);

                    // datas[item.language] = data;

                    languages.push(item.language);
                     data = [];
                     var temp = item.data;
                     for (var j = 0; j < temp.length; j++) {
                         data.push(temp[j].number);
                     }
                     datas[i] = data;
                }

                console.log(datas);

                for (var k = 0; k < languages.length; k++) {
                    var temp = {
                        name: languages[k],
                        type: 'line',
                        data: datas[languages[k]],
                        // data: datas[k],
                        animationDelay: function (idx) {
                            return idx * 10 + 200;
                        }
                    };
                    Series.push(temp);
                }
                console.log(Series);

                myChart.setOption({
                    series: Series
                });

            });
        }

        function loadDataByMonth() {
            myChart.showLoading();

            $.get('../test/CheckLanguageUseByMonth.json').done(function (res) {
                myChart.hideLoading();

                var result = res.data.data;
                var len = result.length;

                var data = [];
                var languages = [];
                var lastpos = ''

                for (var i = 0; i < len; i++) {
                    var item = result[i];

                    var isExist = languages.indexOf(item.language);
                    if (isExist === -1) {
                        if (lastpos !== '')
                            datas[lastpos] = data;
                        languages.push(item.language);
                        data = [];
                        lastpos = item.language;
                    }

                    var temp = item.data;
                    data.push(temp.number);
                }

                console.log(datas);
                for (var k = 0; k < languages.length; k++) {
                    var temp = {
                        name: languages[k],
                        type: 'line',
                        data: datas[languages[k]],
                        animationDelay: function (idx) {
                            return idx * 10 + 200;
                        }
                    };
                    Series.push(temp);
                }
                myChart.setOption({
                    series: Series
                });
                // console.log(Series);
                // console.log(datas);
            });
        }

        var xAxisData = [];
        var languages = [];
        var datas = [];
        var Series = [];

        for (var i = 0; i <= 10; i++) {
            xAxisData.push(2008 + i);
        }

        var myChart = echarts.init(document.getElementById("lineDiv"));
        myChart.setOption({
          title:{
            text:'各语言变化趋势'
          },
          tooltip:{
            trigger:'axis'
          },
          legend:{
              data: ['C', 'C#', 'C++', 'Clojure', 'CoffeScript', 'CSS', 'Go', 'Haskell', 'HTML', 'Java', 'JavaScript', 'Lua', 'Objective-C',
                  'Perl', 'PHP', 'Python', 'R', 'Ruby', 'Scala', 'Shell', 'Swift', 'Viml']
          },
          xAxis:{
            type:'category',
            boundaryGap:false,
              data: xAxisData
          },
          yAxis:{
            type:'value',
            axisLabel:{
              show:true,
              interval:'auto',
                formatter: '{value}' //百分比显示
            },
            show:true
          },
            series: [],

        });
        
        // loadDataByYear();
        
        */
      </script>