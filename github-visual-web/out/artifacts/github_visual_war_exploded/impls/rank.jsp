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
    <title>
        Github Visualization - Rank
    </title>

    <style>
        #div1{
        background:-webkit-linear-gradient(top,pink,white)
    }

    </style>
   


    <script src="../lib/jquery/jquery-3.2.1.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <script src="../lib/echarts.js"></script>
    <link href="../css/bootstrap.min.css.map">
    <link rel="stylesheet" href="../css/bootstrap.min.css">
</head>
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
            <li class="nav-item">
              <a class="nav-link" href="time.jsp">Time</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="geometry.jsp">district</a>
            </li>
            <li class="nav-item active">
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
          <h1 class="display-4">语言排行</h1>
          <p class="lead">
            此页面统计了Github上各个项目的仓库所使用的语言，挑选出了比较热门的语言并将它们使用的占比
            情况以及仓库数量进行了图表展示，您还可以通过点击这些热门语言查看这些语言中比较热门的一些仓
            库
          </p>
        </div>
      </div>

<div class="container" style="margin-top: 50px">
    <div class="row">
        <div class="col-sm-10">

            <table class="table table-striped">
                <caption>世界语言发展趋势</caption>
                <thead>
                <tr>
                    <th scope="col">语言</th>
                    <th scope="col">世界排名</th>
                    <th scope="col">发展趋势</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <th scope="row">Java</th>
                    <td>Mark</td>
                    <td>Otto</td>
                </tr>
                <tr>
                    <th scope="row">C/C++</th>
                    <td>Jacob</td>
                    <td>Thornton</td>
                </tr>
                <tr>
                    <th scope="row">Python</th>
                    <td>Larry</td>
                    <td>the Bird</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="col-sm-2">
            <form>
                <div class="form-check">
                    <input type="checkbox" class="form-check-input" id="exampleCheck1">
                    <label class="form-check-label" for="exampleCheck1">Check me out</label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="" id="defaultCheck1">
                    <label class="form-check-label" for="defaultCheck1">Default checkbox</label>
                </div>
            </form>
        </div>
    </div>
</div>

<div id="pie" class="container" style="height: 800px; width:800px"></div>
<div id="bar" class="container" style="height: 800px; width:800px"></div>
<script>
    var div=document.getElementById("pie");
    var pieChart=echarts.init(div);
    var option={
        series:[
            {
                name:"语言占比图",
                type:"pie",
                radius:"55%",
                data:[
                        {value:10,name:"Java"},
                        {value:10,name:"C/C++"},
                        {value:10,name:"Javascript"},
                        {value:10,name:"Python"},
                        {value:10,name:"Objective-C"}
                    
                ]
            }
        ]
    }
    pieChart.setOption(option);

    var div2=document.getElementById("bar");
    var barChart=echarts.init(div2);
    var option2={
        titile:{
            text:"语言仓库柱状统计图"
        },
        legend:{data:"仓库数量"},
        tooltip:{
            trigger:'item'
        },
        xAxis:{
            type:'category',
            data:["Java","C/C++","JavaScript","Python","Objective-C"]
        },
        yAxis:{
            name:"仓库数量"
        },
        series:[{
            name:"仓库数量",
            type:"bar",
            data:[45,50,55,60,65],
            //柱子的宽度
            
            barWidth:30,
            //配置样式
            itemStyle:{
                //常规
                normal:{
                    color:function(params){
                        var colorList = ['rgb(164,205,238)','rgb(42,170,227)','rgb(25,46,94)','rgb(106,90,205)','rgb(75,0,130'];
                        return colorList[params.dataIndex];
                    }
                },
                //高亮
                emphasis:{
                    shadowBlur:10,
                    shadowOffsetX:0,
                    shadowColor:'rgba(0,0,0,0.5)'
                }
            }
        }],
        /*
        grid:{
            left:'0%',
            right:'10%',
            containLabel:true
        }
        */
        

    }
    barChart.setOption(option2);
</script>


</body>
</html>
