<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Add ODS</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/style.css" rel="stylesheet">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="img/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="img/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="img/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="img/apple-touch-icon-57-precomposed.png">
        <link rel="shortcut icon" href="img/favicon.png">
        <link href="css/headerStyle.css" rel="stylesheet">
	<link href="css/jquery.datetimepicker.css " rel="stylesheet">
        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/scripts.js"></script>
	<script type="text/javascript" src="js/jquery.datetimepicker.js"></script>
        <script>
            $(document).ready(function () {
                $("#cancel").click(function () {
                    clear();
                });
                $("#save").click(function () {
                    submitData();
                });
		$('#date').datetimepicker({
			timepicker: false,
			format:'Y-m-d',
			formatDate:'Y-m-d',
			minDate : new Date()
		});
            });
            function submitData() {
                var user = $("#user").val();
                var password = $("#password").val();
                var sid = $("#sid").val();
                var date = $("#date").val();
		password = encodeURIComponent(password);
                if (validate()) {
                    $.ajax({
                        type: "GET",
                        url: "SubmitData?flag=new&user=" + user + "&password=" + password + "&sid=" + sid + "&date=" + date,
                        contentType: "text",
			cache: false,
                        success: function (data) {
				if(data.trim() == "ussdha=-1|dmussd=1"){
					alert("Rac1 is down and New user created successful on Rac2.");
				var data = {
						"racName": "ussdmis/ussdmis@USSDHA",
						"type": "new",
						"userName": user,
						"password": password,
						"sid": sid,
						"expiry": date
					}
					$.ajax({
						type: "POST",
						url: "CreateFile",
						cache: false,
						data: JSON.stringify(data),
						contentType: "application/json",
						success: function (data) {
							if (data.status == !'success') {
								$("#error").html("Internal System Error! Please try after some time").css({
									"display": "block"
								});
							}
						}
					});
				}
				else if(data.trim() == "ussdha=1|dmussd=-1")
				{
					//$("#sid").val("").focus();
					alert("Rac2 is down and New user created successful on Rac1.");
					var data = {
							"racName": "dmussd/dmussd@dmussd",
							"type": "new",
							"userName": user,
							"password": password,
							"sid": sid,
							"expiry": date
						}
						$.ajax({
							type: "POST",
							url: "CreateFile",
							cache: false,
							data: JSON.stringify(data),
							contentType: "application/json",
							success: function (data) {
								if (data.status == !'success') {
									$("#error").html("Internal System Error! Please try after some time").css({
										"display": "block"
									});
								}
							}
						});
				}
				else if(data.trim() == "ussdha=0|dmussd=0"){
					alert("User already exist  on both Rac1 and Rac2.");
				}
				else if(data.trim() == "ussdha=0|dmussd=1"){
					alert("User already exist on Rac1 and New user created successful on Rac2.");
				}
				else if(data.trim() == "ussdha=1|dmussd=0"){
					alert("User already exist on Rac2 and New user created successful on Rac1.");
				}
				else
				{ 
					clear();
					alert("New user created on both Rac1 and Rac2 successfully.");
				}
/*                            if (data === 'SUCCESS') {
                                clear();
                                alert("Data saved successfuly");
                            } else {
                                alert("Please try again...");
                            }
*/
                        }
                    });
                }
            }
            function validate() {
                var user = $("#user").val();
                var password = $("#password").val();
                var sid = $("#sid").val();
                var date = $("#date").val();
		user = user.trim();
		password = password.trim();
		sid = sid.trim();
		date = date.trim();

                if (user === null || user=="") {
                    alert("Enter DB Username");
                    return false;
                } 

		 if (password === null || password=="") {
                    alert("Enter DB Password");
                    return false;
                }else if(password !=null && password!=""){
			if(password.search("\\|") > -1 || password.search("\\~") > -1 || password.search("\\`") > -1) 
			{	
				alert("|,~,` are reserved characters. Please don't use these characters in password.");
				$("#password").val("").focus();
				return false;
			}
		} 
		
		if (sid === null || sid =="") {
                    alert("Enter DB SID");
                    return false;
                } 
		
		if (date === null || date=="") {
                    alert("Enter Expiry date");
                    return false;
                } 
                
		    return true;
                
            }

            function clear() {
                $("#user").val("");
                $("#password").val("");
                $("#sid").val("");
                $("#date").val("");
            }
        </script>
        
       
    </head>

    <body>
        <div class="header">
              <img src="images/logo.png" alt="Spice Logo" style="float:left;margin-left: 5px">

            <div class="pge-header">
                <ul>
                    <li class="selected"><a href="addNew.jsp">Add New</a></li>
                    <li><a href="viewAll.jsp">View All</a></li>
                </ul>
            </div>
        </div>
        <div class="container">
            <div class="row clearfix" style="margin-top:100px;">
                <div class="col-md-4 column"></div>
                <div class="col-md-4 column frmContainer">
                    
                        <form role="form">
                            <div class="form-group">
                                <label for="exampleInputEmail1">DB User Name</label><input type="text" class="form-control" id="user">
                            </div>
                            <div class="form-group">
                                <label for="exampleInputPassword1">DB Password</label><input type="password" class="form-control" id="password">
                            </div>
                            <div class="form-group">
                                <label for="exampleInputEmail1">DB SID</label><input type="text" class="form-control" id="sid">
                            </div>
                            <div class="form-group">
                                <label for="exampleInputPassword1">Expiry Date</label><input type="text" class="form-control" id="date">
                            </div><button type="button" id="save" style="float:right" class="btn btn-default">Save</button><button type="button" id="cancel" style="margin-right:10px;float:right" class="btn btn-default">Cancel</button>
                        </form>
                    
                </div>
            </div>
        </div>
    </body>
</html>
