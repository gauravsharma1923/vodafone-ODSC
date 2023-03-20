<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>ODS Details</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="stylesheet" type="text/css" href="css/jquery.dataTables.css">
<link rel="stylesheet" type="text/css"
	href="css/jquery.datetimepicker.css">

<link href="css/style.css" rel="stylesheet">
<link href="css/headerStyle.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" src="js/jquery.datetimepicker.js"></script>

<script>
$(document).ready(function () {
	getData();
	datePik();
	$('body').on('click', '.edit-btn', function () {
		var ar = new Array();
		for (var i = 1; i <= 4; i++) {
			var spanData = $(this).closest('tr').find('td:nth-child(' + i + ')').find('span');
			var txtVal = $(spanData).text();
			//console.log($(spanData).text()+"  ++test");
			$(spanData).remove();
			var inputField = document.createElement("input");
			$(inputField).attr("type", "text");
			$(inputField).addClass("inputField");
			//txtVal = decodeURIComponent(txtVal);
			//alert(txtVal);
			$(inputField).val(txtVal);
			if (i == 4) {
				$(inputField).attr("id", "data0");
				$(inputField).datetimepicker({
					timepicker: false,
					format: 'Y-m-d',
					formatDate: 'Y-m-d',
					minDate: new Date()
				});
				// $(inputField).addClass("date-pick");
			}
			ar[i - 1] = txtVal;
			$(this).closest('tr').find('td:nth-child(' + i + ')').append(inputField);
		}
		$(this).closest('tr').find(".save-btn").prop("disabled", false);
		$(".edit-btn").prop("disabled", true);
		var prnt = $(this).parent();
		$(this).remove();
		var button = document.createElement("button");
		button.appendChild(document.createTextNode("Cancel"));
		$(button).attr({
			"data-un": ar[0],
			"data-pd": ar[1],
			"data-sid": ar[2],
			"data-dt": ar[3]
		}).addClass("cancel-btn");
		$(prnt).append(button);
	});
	$("body").on('click', ".cancel-btn", function () {
		var txtVal = $(this).attr("data-un");
		var span = document.createElement("span");
		var txtNode = document.createTextNode(txtVal);
		span.appendChild(txtNode);
		$(this).closest('tr').find('td:nth-child(1)').append(span);
		$(this).closest('tr').find('td:nth-child(1)').find("input[type=text]").remove();
		txtVal = $(this).attr("data-pd");
		span = document.createElement("span");
		txtNode = document.createTextNode(txtVal);
		span.appendChild(txtNode);
		$(this).closest('tr').find('td:nth-child(2)').append(span);
		$(this).closest('tr').find('td:nth-child(2)').find("input[type=text]").remove();
		txtVal = $(this).attr("data-sid");
		span = document.createElement("span");
		txtNode = document.createTextNode(txtVal);
		span.appendChild(txtNode);
		$(this).closest('tr').find('td:nth-child(3)').append(span);
		$(this).closest('tr').find('td:nth-child(3)').find("input[type=text]").remove();
		txtVal = $(this).attr("data-dt");
		span = document.createElement("span");
		txtNode = document.createTextNode(txtVal);
		span.appendChild(txtNode);
		$(this).closest('tr').find('td:nth-child(4)').append(span);
		$(this).closest('tr').find('td:nth-child(4)').find("input[type=text]").remove();
		var prnt = $(this).parent();
		$(this).remove();
		var btn = document.createElement("button");
		$(btn).addClass("edit-btn");
		textNode = document.createTextNode("Edit");
		btn.appendChild(textNode);
		$(prnt).append(btn);
		$(".edit-btn").prop("disabled", false);
		$(".save-btn").prop("disabled", true);
	});
	$('body').on('click', '.save-btn', function () {
		var user = $(this).closest('tr').find('td:nth-child(1)').find('input[type=text]').val();
		var pass = $(this).closest('tr').find('td:nth-child(2)').find('input[type=text]').val();
		var sid = $(this).closest('tr').find('td:nth-child(3)').find('input[type=text]').val();
		var hiddensid = $(this).attr('data-sid');
		var date = $(this).closest('tr').find('td:nth-child(4)').find('input[type=text]').val();
		var curbtn = this;
		user = user.trim();
		pass = pass.trim();
		sid = sid.trim();
		date = date.trim();
		if (user == "" || user == null) {
			alert("Enter DB Username");
			$(this).closest('tr').find('td:nth-child(1)').find('input[type=text]').focus();
			return null;
		}
		if (pass == "" || pass == null) {
			alert("Enter DB Password");
			$(this).closest('tr').find('td:nth-child(2)').find('input[type=text]').focus();
			return null;
		} else if (pass != "" && pass != null) {
			if (pass.search("\\|") > -1 || pass.search("\\~") > -1 || pass.search("\\`") > -1) {
				alert("|,~,` are reserved characters. Please don't use these characters in password.");
				$(this).closest('tr').find('td:nth-child(2)').find('input[type=text]').val("").focus();
				return null;
			}
		}
		if (sid == "" || sid == null) {
			alert("Enter DB SID");
			$(this).closest('tr').find('td:nth-child(3)').find('input[type=text]').focus();
			return null;
		}
		if (date == "" || date == null) {
			alert("Enter Expiry Date");
			$(this).closest('tr').find('td:nth-child(4)').find('input[type=text]').focus();
			return null;
		}
		pass = encodeURIComponent(pass);
		//alert("User=" + user + " Pass=" + pass + " Sid=" + sid + " Date=" + date+" hiddensid= "+hiddensid);
		$.ajax({
			type: "GET",
			url: "SubmitData?flag=update&user=" + user + "&password=" + pass + "&sid=" + sid + "&date=" + date + "&cursid=" + hiddensid,
			contentType: "text",
			success: function (data) {
				console.log('data', data);
				if (data == '-1' || data == -1) {
					alert("Internal System Error! Please Try Again");
				} else if (data == "0" || data == 0) {
					alert("SID Already Exist");
				} else {
					$(curbtn).attr('data-sid', sid).prop('disabled', true);
					$(curbtn).closest('tr').find('td:nth-child(5)').find("button.cancel-btn").remove();
					var btn = document.createElement("button");
					$(btn).addClass("edit-btn");
					textNode = document.createTextNode("Edit");
					btn.appendChild(textNode);
					$(curbtn).closest('tr').find('td:nth-child(5)').append(btn);
					$(".edit-btn").prop("disabled", false);
					for (var i = 1; i <= 4; i++) {
						var spanData = $(curbtn).closest('tr').find('td:nth-child(' + i + ')').find('input[type=text]');
						var txtVal = $(spanData).val();
						$(spanData).remove();
						var span = document.createElement("span");
						var txtNode = document.createTextNode(txtVal);
						span.appendChild(txtNode);
						$(curbtn).closest('tr').find('td:nth-child(' + i + ')').append(span);
					}
					console.log("----->" + data.trim());
					if (data.trim() == 'ussdha=1|dmussd=1') {
						alert("Rac1 and Rac2 both database updated successful." + data);
					} else if (data.trim() == 'ussdha=-1|dmussd=1') {
						alert("Rac1 is down and data updated successful in Rac2.");
						var data = {
							"racName": "ussdmis/ussdmis@USSDHA",
							"type": "update",
							"userName": user,
							"password": pass,
							"sid": sid,
							"expiry": date,
							"cursid": hiddensid
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
					} else if (data.trim() == 'ussdha=1|dmussd=-1') {
						alert("Rac2 is down and data updated successful in Rac1." + data);
						var data = {
							"racName": "dmussd/dmussd@dmussd",
							"type": "update",
							"userName": user,
							"password": pass,
							"sid": sid,
							"expiry": date,
							"cursid": hiddensid
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
					}else{
						alert("Failed");
					}
				}
			}
		});
	});
	$('body').on('click', '.delete-btn', function () {
		var values = [];
		for (var i = 1; i <= 4; i++) {
			var spanData = $(this).closest('tr').find('td:nth-child(' + i + ')').find('span');
			values[i] = $(spanData).text();
		}
		$.ajax({
			type: "GET",
			url: "SubmitData?flag=delete&user=" + values[1] + "&password=" + values[2] + "&sid=" + values[3] + "&date=" + values[4] + "&cursid=" + values[3],
			contentType: "text",
			success: function (data) {
				if (data.trim() == "ussdha=-1|dmussd=-1") {
					$("#error").html("Internal System Error! Please try after some time").css({
						"display": "block"
					});
				}
				else if (data.trim() == "ussdha=1|dmussd=1") {
					alert("Record Deleted Successfully from both Racs.");
					location.reload();
				} 
				else if (data.trim() == "ussdha=0|dmussd=0") {
					alert("Record Not found on both Racs.");
					location.reload();
				}
				else if (data.trim() == "ussdha=1|dmussd=0") {
					alert("Record successfully deleted on Rac1.");
				} 
				else if (data.trim() == "ussdha=1|dmussd=-1") {
					alert("Record successfully deleted on Rac1 & server error from Rac2");
				} 
				else if (data.trim() == "ussdha=-1|dmussd=1") {
					alert("Record successfully deleted on Rac2 & server error from Rac1.");
				} 
				else if (data.trim() == "ussdha=0|dmussd=1") {
					alert("Record successfully deleted on Rac2.");
				} else {
					$("#error").html("Internal System Error! Please try after some time").css({
						"display": "block"
					});
				}
			}
		});
	});
});

function datePik() {
	$(document).on('focus', ".date-pick", function () {
		$(this).datepicker({
			dateFormat: 'yy-mm-dd'
		});
	});
}

function getData() {
	$.ajax({
		type: "GET",
		url: "ViewData",
		cache: false,
		contentType: "text",
		success: function (data) {
			if (data == '-1' || data == -1) {
				$("#error").html("Internal System Error! Please try after some time").css({
					"display": "block"
				});
			} else if (data == "1" || data == 1) {
				$("#error").html("No Record Found.").css({
					"display": "block"
				});
			} else {
				generateTable(data);
			}
		}
	});
}

function generateTable(data) {
	var arr = data.split("~");
	var thead = document.createElement("thead");
	var tr = document.createElement("tr");
	var th = document.createElement("th");
	var textNode = document.createTextNode("DB Username");
	th.appendChild(textNode);
	tr.appendChild(th);
	th = document.createElement("th");
	textNode = document.createTextNode("DB Password");
	th.appendChild(textNode);
	tr.appendChild(th);
	th = document.createElement("th");
	textNode = document.createTextNode("DB SID");
	th.appendChild(textNode);
	tr.appendChild(th);
	th = document.createElement("th");
	textNode = document.createTextNode("Expiry Date");
	th.appendChild(textNode);
	tr.appendChild(th);
	th = document.createElement("th");
	textNode = document.createTextNode("Edit");
	th.appendChild(textNode);
	tr.appendChild(th);
	th = document.createElement("th");
	textNode = document.createTextNode("Save");
	th.appendChild(textNode);
	tr.appendChild(th);
	th = document.createElement("th");
	textNode = document.createTextNode("Delete");
	th.appendChild(textNode);
	tr.appendChild(th);
	thead.appendChild(tr);
	var tbody = document.createElement("tbody");
	for (var i = 0; i < arr.length; i++) {
		tbody.appendChild(createRow(arr[i]));
	}
	$("#results").append(thead);
	$("#results").append(tbody);
	$('#results').DataTable();
	$("#results").find("tr:nth-child(even)").addClass("trOdd");
}
var id = 0;

function createRow(data) {
	var arr = data.split("|");
	tr = document.createElement("tr");
	var sid;
	id++;
	for (var i = 0; i < arr.length; i++) {
		var td = document.createElement("td");
		var span = document.createElement("span");
		textNode = document.createTextNode(arr[i]);
		//console.log(arr[i]+" arr[i]Val");
		span.appendChild(textNode);
		td.appendChild(span);
		tr.appendChild(td);
		sid = arr[2];
	}
	var td = document.createElement("td");
	var btn = document.createElement("button");
	$(btn).addClass("edit-btn");
	textNode = document.createTextNode("Edit");
	btn.appendChild(textNode);
	td.appendChild(btn);
	tr.appendChild(td);
	td = document.createElement("td");
	btn = document.createElement("button");
	$(btn).addClass("save-btn").attr({
		'data-sid': sid,
		"disabled": true
	});
	textNode = document.createTextNode("Save");
	btn.appendChild(textNode);
	td.appendChild(btn);
	tr.appendChild(td);
	td = document.createElement("td");
	btn = document.createElement("button");
	$(btn).addClass("delete-btn");
	textNode = document.createTextNode("Delete");
	/* btn.onclick = function(){
			console.log('hariom');
			generateTable(data);}; */
	btn.appendChild(textNode);
	td.appendChild(btn);
	tr.appendChild(td);
	return tr;
}
</script>
</head>

<body>
	<div class="header">
		<img src="images/logo.png" alt="Spice Logo"
			style="float: left; margin-left: 5px">

		<div class="pge-header">
			<ul>
				<li><a href="addNew.jsp">Add New</a></li>
				<li class="selected"><a href="viewAll.jsp">View All</a></li>
			</ul>
		</div>
	</div>
	<div class="container">
		<h1 style="color: red; display: none; text-align: center" id="error"></h1>
		<table id="results" class="table">

		</table>
	</div>
</body>
</html>
