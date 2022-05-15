<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Credit Card Payment</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />

        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
        

        <script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
    </head>
    <body>
        <div>
            <ul>
                <li><a href="index.jsp">Add Card</a></li>
                <li><a href="CardAPI">View Card Details</a></li>
            </ul>
        </div>
        <hr></hr>
        <%
            String message = (String) request.getAttribute("alertMsg");
            if (message != null) {
                if (message == "Card Details added Successfully!") {%>
        <div class="alert">
            <span class="closebtn" onclick="this.parentElement.style.display = 'none';">&times;</span> 
            <strong>Success!</strong> <%= message%>
        </div> 
        <% } else {%>
        <div class="alert1">
            <span class="closebtn" onclick="this.parentElement.style.display = 'none';">&times;</span> 
            <strong>Failed!</strong> <%= message%>
        </div> 

        <%
                }
            }
        %>

        <div class="container">
            <div class="row">
                <div class="col-xs-12 col-md-4 col-md-offset-4">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div class="row">
                                <h3 class="text-center">Card Details</h3>
                                <img class="img-responsive cc-img" src="http://www.prepbootstrap.com/Content/images/shared/misc/creditcardicons.png">
                            </div>
                        </div>
                        <div class="panel-body">
                            <form method="post" action="CardAPI">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <div class="form-group">
                                            <label>CARD NUMBER</label>
                                            <div class="input-group">
                                                <input name="card" type="number" class="form-control" placeholder="Valid Card Number" onKeyDown="if (this.value.length == 16 && event.keyCode!=8)return false;"  required />
                                                <span class="input-group-addon"><span class="fa fa-credit-card"></span></span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-7 col-md-7">
                                        <div class="form-group">
                                            <label><span class="hidden-xs">EXPIRY DATE</span><span class="visible-xs-inline">EXP</span> DATE</label>
                                            <input name="date" type="tel" class="form-control" placeholder="MM / YY" pattern="(?:0[1-9]|1[0-2])/[0-9]{2}" required/>
                                        </div>
                                    </div>
                                    <div class="col-xs-5 col-md-5 pull-right">
                                        <div class="form-group">
                                            <label>CV CODE</label>
                                            <input name="cvc" type="number" class="form-control" placeholder="CVC" onKeyDown="if (this.value.length == 3 && event.keyCode!=8)
                                                        return false;" required />
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12">
                                        <div class="form-group">
                                            <label>CARD OWNER</label>
                                            <input name="name" type="text" class="form-control" placeholder="Card Owner Name" required />
                                        </div>
                                    </div>
                                </div>

                        </div>
                        <div class="panel-footer">
                            <div class="row">
                                <div class="col-xs-12">
                                    <button class="btn btn-warning btn-lg btn-block">Add Card</button>
                                </div>
                            </div>
                        </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <style>
            .cc-img {
                margin: 0 auto;
            }
            ul {
                list-style-type: none;
                margin: 0;
                padding: 0;
                overflow: hidden;
                background-color: #04AA6D;
            }

            li {
                float: left;
            }

            li a {
                display: block;
                color: white;
                text-align: center;
                padding: 14px 16px;
                text-decoration: none;
            }

            li a:hover:not(.active) {
                background-color: #111;
            }

            .active {
                background-color: #04AA6D;
            }
            .alert {
                padding: 20px;
                background-color: #31b0d5;
                color: white;
            }
            .alert1 {
                padding: 20px;
                background-color: red;
                color: white;
            }

            .closebtn {
                margin-left: 15px;
                color: white;
                font-weight: bold;
                float: right;
                font-size: 22px;
                line-height: 20px;
                cursor: pointer;
                transition: 0.3s;
            }

            .closebtn:hover {
                color: black;
            }
        </style>
        <!--        <script type="text/javascript">
            var msg = "<%=message%>";
            alert(msg);
        </script>-->
    </body>
</html>