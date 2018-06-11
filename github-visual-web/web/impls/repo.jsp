<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2018/4/12
  Time: 19:33
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Github Visualization - Repository Visualization</title>
    <script src="https://d3js.org/d3.v3.min.js"></script>
    <script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
    <%--<script src="../lib/jquery/jquery-3.2.1.min.js"></script>--%>
    <script src="../js/bootstrap.min.js"></script>
    <%--<script src="../js/d3.min.js"></script>--%>

    <script src="../js/jquery.tipsy.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/jquery.tipsy/1.0.2/jquery.tipsy.css" rel="stylesheet"
          type="text/css"/>

    <link href="../css/bootstrap.min.css.map">
    <link rel="stylesheet" href="../css/bootstrap.min.css">

    <link rel="stylesheet" href="../css/main.css">

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="https://cdnjs.cloudflare.com/ajax/libs/jquery.tipsy/1.0.2/jquery.tipsy.css" rel="stylesheet"
          type="text/css"/>
</head>
<body>

<%--<div class="jumbotron jumbotron-fluid" style="background:-webkit-linear-gradient(top,lightseagreen,white)">
</div>
<div class="container">
    <h3 class="align-content-center">Please enter the repository's name:</h3>

    <div id="prompt">--%>
<%--    <form class="input-group mb-6" style="margin-top: 30px" action="repo.jsp" method="get">
        <input type="text" class="form-control" placeholder="User/Organization" aria-label="User/Organization"
               aria-describedby="basic-addon2" name="owner" id="owner">
        <input type="text" class="form-control" placeholder="Repository" aria-label="Repository"
               aria-describedby="basic-addon2" name="repo" id="repo">
        <div class="input-group-append">
            <button class="btn btn-outline-secondary" type="submit">Go!</button>
        </div>
    </form>--%>
<%--  <form class="input-group mb-6" id="signup-form" style="margin-top: 30px" action="javascript:getRepo()">
  <!--<form class="input-group mb-6" id="signup-form" style="margin-top: 30px" action="javascript:getRepo()">-->
      <input type="text" class="form-control" placeholder="User/Organization" aria-label="User/Organization"
             aria-describedby="basic-addon2" name="owner" id="owner">
      <input type="text" class="form-control" placeholder="Repository" aria-label="Repository"
             aria-describedby="basic-addon2" name="repo" id="repo">
      <div class="input-group-append">
          <button class="btn btn-outline-secondary" type="submit">Go!</button>
      </div>
  </form>

  </div>
  <p>Enter a GitHub user/organization name and repository and hit Go! (<a href="?owner=atom&repo=atom">Try atom/atom</a>) <br />
      Scroll to zoom, click and drag to pan. <br />
      <i style="color:#f00">Warning:</i> Large repos can get laggy
  </p>

  </div>
<section id="header" class="align-content-center">
  <img id="logo"/>
</section>--%>
<%--<div id="graph"></div>--%>

<section id="header" class="jumbotron jumbotron-fluid"
         style="padding: 50px; background:-webkit-linear-gradient(top,lightseagreen,white)">

    <h3 class="align-content-center">Please enter the repository's name:</h3>

    <p>Enter a GitHub user/organization name and repository and hit Go! (<a
            href="?owner=software-case-course&repo=github_visual">Try this</a>) <br/>
        Scroll to zoom, click and drag to pan. <br/>
        <i style="color:#f00">Warning:</i> Large repos can get laggy
    </p>

</section>

<div class="row">

    <div id="prompt" class="col-10 offset-1">
        <form class="start input-group mb-6" id="signup-form" action="javascript:getRepo()">
            <input type="text" name="owner" id="owner" class="form-control" placeholder="User/Organization"/>
            <input type="text" name="repo" id="repo" class="form-control" placeholder="Repository"/>
            <input class="btn btn-outline-primary" type="submit" value="Go!"/>
            <a class="btn btn-outline-secondary" href="../index.html">Back</a>
        </form>

    </div>
</div>


<div class="container" align="center">
    <img id="logo" style="margin-top: 50px"/>
</div>

<div id="graph"></div>

<script src="../js/repo-directory-structure.js" onload="checkQuery();"></script>

<
script >
(function (i, s, o, g, r, a, m) {
    i['GoogleAnalyticsObject'] = r;
    i[r] = i[r] || function () {
        (i[r].q = i[r].q || []).push(arguments)
    }, i[r].l = 1 * new Date();
    a = s.createElement(o),
        m = s.getElementsByTagName(o)[0];
    a.async = 1;
    a.src = g;
    m.parentNode.insertBefore(a, m)
})(window, document, 'script', 'https://www.google-analytics.com/analytics.js', 'ga');

ga('create', 'UA-55042305-4', 'auto');
ga('send', 'pageview');
</script>
</body>
</html>
