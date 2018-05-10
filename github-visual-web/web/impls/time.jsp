<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2018/4/12
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>
        Github Visualization - Filtered by time
    </title>
    <script src="../lib/jquery/jquery-3.2.1.min.js"></script>
    <script src="https://unpkg.com/popper.js/dist/umd/popper.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts.min.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts-gl/echarts-gl.min.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts-stat/ecStat.min.js"></script>
    <script type="text/javascript"
            src="http://echarts.baidu.com/gallery/vendors/echarts/extension/dataTool.min.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/china.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/world.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ZUONbpqGBsYGXNIYHicvbAbM"></script>
    <script type="text/javascript"
            src="http://echarts.baidu.com/gallery/vendors/echarts/extension/bmap.min.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/simplex.js"></script>

    <link href="../css/bootstrap.min.css.map">
    <link rel="stylesheet" href="../css/bootstrap.min.css">
</head>
<body onload="loadDataByYear()">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="#">Github Visualization</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" 
        data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon">IT IS A SPAN</span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav">
            <li class="nav-item ">
              <a class="nav-link" href="../index.jsp">Home<span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item active">
              <a class="nav-link" href="time.jsp">Time</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="geometry.jsp">district</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="rank.jsp">Language Rank</a>
              </li>
            <li class="nav-item ">
              <a class="nav-link" href="trend.jsp">Language Trend</a>
            </li>
          </ul>
        </div>
      </nav>

    <div id="container" style="height: 500px; margin-top: 50px"></div>
    <form style="padding: 20px">
        <div class="form-group">
            <label for="FormControlSelect">Options</label>
            <select class="form-control" id="FormControlSelect" onchange="selectOnchang(this)">
                <option value="CheckLanguageUseByYear">By Year</option>
                <option value="CheckLanguageUseByMonth">By Month</option>
            </select>
        </div>
    </form>

<script type="text/javascript">

    function loadDataByYear() {
        myChart.showLoading();
        this.Series = [];
        $.get('../test/checkByYear.json').done(function (result) {

            myChart.hideLoading();

            // console.log(result);
            // data = eval('('+result+')');

            var len = result.length;

            var data = [];
            var languages = [];
            var datas = [];
            for (var i = 0; i < len; i++) {
                var item = result[i];
                // console.log(item.language);
                languages.push(item.language);
                // console.log(item);
                data = [];
                var temp = item.data;
                // console.log(temp);
                for (var j = 0; j < temp.length; j++) {
                    data.push(temp[j].num);
                }
                // console.log(data);
                datas[i] = data;
            }
            // console.log(datas);
            // console.log(languages);

            for (var k = 0; k < languages.length; k++) {
                var temp = {
                    name: languages[k],
                    // type: 'line',
                    type: 'bar',
                    data: datas[k],
                    animationDelay: function (idx) {
                        return idx * 10 + 200;
                    }
                };
                // console.log(temp);
                Series.push(temp);
            }
            console.log(Series);

            // option.series = Series;
            // console.log(option.series);
            myChart.setOption({
                series: Series
            });

        });
    }

    function loadDataByMonth() {
        myChart.showLoading();
        this.Series = [];
        $.get('../test/checkByMonth.json').done(function (result) {

            myChart.hideLoading();

            // console.log(result);
            // data = eval('('+result+')');

            var len = result.length;

            var data = [];
            var languages = [];
            var datas = [];
            for (var i = 0; i < len; i++) {
                var item = result[i];
                // console.log(item.language);
                languages.push(item.language);
                // console.log(item);
                data = [];
                var temp = item.data;
                // console.log(temp);
                for (var j = 0; j < temp.length; j++) {
                    data.push(temp[j].num);
                }
                // console.log(data);
                datas[i] = data;
            }
            // console.log(datas);
            // console.log(languages);

            for (var k = 0; k < languages.length; k++) {
                var temp = {
                    name: languages[k],
                    type: 'bar',
                    // type: 'line',
                    data: datas[k],
                    animationDelay: function (idx) {
                        return idx * 10 + 200;
                    }
                };
                // console.log(temp);
                Series.push(temp);
            }
            // console.log(Series);

            // option.series = Series;
            // console.log(option.series);
            myChart.setOption({
                series: Series
            });
        });
    }

    function selectOnchang(obj) {
        var value = obj.options[obj.selectedIndex].value;
        // alert(value);
        if (value == "CheckLanguageUseByYear") {
            // alert("CheckLanguageUseByYear 2");
            xAxisData = [];
            this.datas = [];
            this.Series = [];
            for (var i = 0; i <= 5; i++) {
                xAxisData.push(2013 + i);
            }
            myChart.setOption({
                xAxis: {
                    data: xAxisData
                }
            });

            loadDataByYear();

        } else if (value == "CheckLanguageUseByMonth") {
            // alert("CheckLanguageUseByMonth 2");
            xAxisData = [];
            this.datas = [];
            this.Series = [];
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

    var dom = document.getElementById("container");
    var myChart = echarts.init(dom);

    var xAxisData = [];
    /*    var data_C = [];
        var data_C_hash = [];
        var data_C_plus = [];
        var data_Clojure = [];
        var data_CoffeScript = [];
        var data_CSS = [];
        var data_Go = [];
        var data_Haskell = [];
        var data_HTML = [];
        var data_Java = [];
        var data_Javascript = [];
        var data_Lua = [];*/

    var languages = [];
    var datas = [];
    var Series = [];

    for (var i = 0; i <= 5; i++) {
        xAxisData.push(2013 + i);
    }

    myChart.setOption({
        title: {
            text: '各语言随时间的变化趋势（仓库数量）'
        },
        legend: {
            data: ['C', 'C#', 'C++', 'Clojure', 'CoffeScript', 'CSS', 'Go', 'Haskell', 'HTML', 'Java', 'JavaScript', 'Lua', 'Objective-C',
                'Perl', 'PHP', 'Python', 'R', 'Ruby', 'Scala', 'Shell', 'Swift', 'Viml'],
            align: 'left'
        },
        toolbox: {
            // y: 'bottom',
            feature: {
                magicType: {
                    type: ['stack', 'tiled']
                },
                dataView: {},
                saveAsImage: {
                    pixelRatio: 2
                }
            }
        },
        tooltip: {
            // trigger:'axis'
        },
        xAxis: {
            data: xAxisData,
            silent: false,
            splitLine: {
                show: false
            }
        },
        yAxis: {
            /*  type:'value',
              axisLabel:{
                  show:true,
                  interval:'auto',
                  formatter:'{value}'
              },
              show:true*/
        },
        series: [
            /*{
            name: 'C',
            type: 'bar',
            data: data_C,
            animationDelay: function (idx) {
                return idx * 10;
            }
        }, {
            name: 'C#',
            type: 'bar',
            data: data_C_hash,
            animationDelay: function (idx) {
                return idx * 10 + 100;
            }
        }, {
            name: 'C++',
            type: 'bar',
            data: data_C_plus,
            animationDelay: function (idx) {
                return idx * 10 + 200;
            }
        }, {
            name: 'Clojure',
            type: 'bar',
            data: data_Clojure,
            animationDelay: function (idx) {
                return idx * 10 + 300;
            }
        }, {
            name: 'CoffeScript',
            type: 'bar',
            data: data_CoffeScript,
            animationDelay: function (idx) {
                return idx * 10;
            }
        }, {
            name: 'CSS',
            type: 'bar',
            data: data_CSS,
            animationDelay: function (idx) {
                return idx * 10;
            }
        }, {
            name: 'Go',
            type: 'bar',
            data: data_Go,
            animationDelay: function (idx) {
                return idx * 10;
            }
        }, {
            name: 'Haskell',
            type: 'bar',
            data: data_Haskell,
            animationDelay: function (idx) {
                return idx * 10;
            }
        }, {
            name: 'Html',
            type: 'bar',
            data: data_HTML,
            animationDelay: function (idx) {
                return idx * 10;
            }
        }, {
            name: 'Java',
            type: 'bar',
            data: data_Java,
            animationDelay: function (idx) {
                return idx * 10;
            }
        }, {
            name: 'Javascript',
            type: 'bar',
            data: data_Javascript,
            animationDelay: function (idx) {
                return idx * 10;
            }
        }, {
            name: 'Lua',
            type: 'bar',
            data: data_Lua,
            animationDelay: function (idx) {
                return idx * 10;
            }
        }, */
        ],
        animationEasing: 'elasticOut',
        animationDelayUpdate: function (idx) {
            return idx * 5;
        }
    });

    //i just found out that this isn't the right page ...
    //FFFFFFFFFFFFFFFFFFFFFFFFFF
</script>
</body>
</html>
