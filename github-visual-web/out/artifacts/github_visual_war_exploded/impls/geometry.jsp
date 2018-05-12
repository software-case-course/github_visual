<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2018/4/12
  Time: 14:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>
        Github Visualization - Filtered by region
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

<style>
    #div1{
        background:-webkit-linear-gradient(top,greenyellow,white)
    }

    li:hover{
        background: blue;
    }
</style>
<body>
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
            <li class="nav-item ">
              <a class="nav-link" href="time.jsp">Time</a>
            </li>
            <li class="nav-item active">
              <a class="nav-link" href="geometry.jsp">district</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="rank.jsp">Language Rank</a>
            <li class="nav-item">
              <a class="nav-link" href="trend.jsp">Language Trend</a>
            </li>
            </li>
          </ul>
        </div>
      </nav>

      <div class="jumbotron jumbotron-fluid" id="div1">
        <div class="container" >
          <h1 class="display-4">查看地域分布</h1>
          <p class="lead">
            在这里，我们根据Github上用户登记的个人地址，为你展示了语言排行中几种较为热门的语言
            在不同地区的使用占比情况<br/>
            您可以分别在世界地图和中国地图范围内查看您想查看的语言。
          </p>
        </div>
      </div>

    <div class="container" style="padding:50px">
    
        <div class="container" style="padding:50px">
            您当前查看的的语言为:
            <select>
                <option>Java</option>
                <option>C/C++</option>
                <option>Python</option>
                <option>JavaScript</option>
                <option>Objective-C</option>
            </select>
        </div>
    
      <div class="btn-group " data-toggle="buttons" align="center">
        <label class="btn btn-primary btn-lg ">
            <input type="radio" name="map" id="map1">查看世界地图
        </label>
    
        <label class="btn btn-primary btn-lg">
            <input type="radio" name="map" id="map2">查看中国地图
        </label>
      </div>
    </div>


      <div class="container" id="map"></div>
    
    


<script type="text/javascript">
    var dom = document.getElementById("map");
    var myChart = echarts.init(dom);
    var app = {};
    option = null;

    function randomData() {
        return Math.round(Math.random() * 1000);
    }

    option = {
        title: {
            text: 'iphone销量',
            subtext: '纯属虚构',
            left: 'center'
        },
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['iphone3', 'iphone4', 'iphone5']
        },
        visualMap: {
            min: 0,
            max: 2500,
            left: 'left',
            top: 'bottom',
            text: ['高', '低'],           // 文本，默认为数值文本
            calculable: true
        },
        toolbox: {
            show: true,
            orient: 'vertical',
            left: 'right',
            top: 'center',
            feature: {
                dataView: {readOnly: false},
                restore: {},
                saveAsImage: {}
            }
        },
        series: [
            {
                name: 'iphone3',
                type: 'map',
                mapType: 'china',
                roam: false,
                label: {
                    normal: {
                        show: true
                    },
                    emphasis: {
                        show: true
                    }
                },
                data: [
                    {name: '北京', value: randomData()},
                    {name: '天津', value: randomData()},
                    {name: '上海', value: randomData()},
                    {name: '重庆', value: randomData()},
                    {name: '河北', value: randomData()},
                    {name: '河南', value: randomData()},
                    {name: '云南', value: randomData()},
                    {name: '辽宁', value: randomData()},
                    {name: '黑龙江', value: randomData()},
                    {name: '湖南', value: randomData()},
                    {name: '安徽', value: randomData()},
                    {name: '山东', value: randomData()},
                    {name: '新疆', value: randomData()},
                    {name: '江苏', value: randomData()},
                    {name: '浙江', value: randomData()},
                    {name: '江西', value: randomData()},
                    {name: '湖北', value: randomData()},
                    {name: '广西', value: randomData()},
                    {name: '甘肃', value: randomData()},
                    {name: '山西', value: randomData()},
                    {name: '内蒙古', value: randomData()},
                    {name: '陕西', value: randomData()},
                    {name: '吉林', value: randomData()},
                    {name: '福建', value: randomData()},
                    {name: '贵州', value: randomData()},
                    {name: '广东', value: randomData()},
                    {name: '青海', value: randomData()},
                    {name: '西藏', value: randomData()},
                    {name: '四川', value: randomData()},
                    {name: '宁夏', value: randomData()},
                    {name: '海南', value: randomData()},
                    {name: '台湾', value: randomData()},
                    {name: '香港', value: randomData()},
                    {name: '澳门', value: randomData()}
                ]
            },
            {
                name: 'iphone4',
                type: 'map',
                mapType: 'china',
                label: {
                    normal: {
                        show: true
                    },
                    emphasis: {
                        show: true
                    }
                },
                data: [
                    {name: '北京', value: randomData()},
                    {name: '天津', value: randomData()},
                    {name: '上海', value: randomData()},
                    {name: '重庆', value: randomData()},
                    {name: '河北', value: randomData()},
                    {name: '安徽', value: randomData()},
                    {name: '新疆', value: randomData()},
                    {name: '浙江', value: randomData()},
                    {name: '江西', value: randomData()},
                    {name: '山西', value: randomData()},
                    {name: '内蒙古', value: randomData()},
                    {name: '吉林', value: randomData()},
                    {name: '福建', value: randomData()},
                    {name: '广东', value: randomData()},
                    {name: '西藏', value: randomData()},
                    {name: '四川', value: randomData()},
                    {name: '宁夏', value: randomData()},
                    {name: '香港', value: randomData()},
                    {name: '澳门', value: randomData()}
                ]
            },
            {
                name: 'iphone5',
                type: 'map',
                mapType: 'china',
                label: {
                    normal: {
                        show: true
                    },
                    emphasis: {
                        show: true
                    }
                },
                data: [
                    {name: '北京', value: randomData()},
                    {name: '天津', value: randomData()},
                    {name: '上海', value: randomData()},
                    {name: '广东', value: randomData()},
                    {name: '台湾', value: randomData()},
                    {name: '香港', value: randomData()},
                    {name: '澳门', value: randomData()}
                ]
            }
        ]
    };


    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
</script>



</body>
</html>
