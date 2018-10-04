$(function() {
  $("#dob").datepicker({
    format: 'mm-dd-yyyy',
    endDate: '-1d',
    autoclose: true
  })
});


//To validate the form
function validateForm() {

  //To reset the warning messages
  document.getElementById("fname_msg").innerHTML = "";
  document.getElementById("lname_msg").innerHTML = "";
  document.getElementById("dob_msg").innerHTML = "";
  document.getElementById("ph_msg").innerHTML = "";
  document.getElementById("email_msg").innerHTML = "";

  var i = 0;
  var flag = 0;
  //regular expressions for the validation
  var freg = /^[a-zA-Z\s]{3,20}$/;
  var lreg = /^[a-zA-Z\s]{3,20}$/;
  var dob_given = document.forms["form1"]["dob"].value;
  var dobreg = new Date();
  var tod = new Date(document.getElementById("dob").value);
  var emailreg = /^[A-Za-z0-9][A-Za-z0-9._%+-]{0,63}@(?:[A-Za-z0-9-]{1,5}\.){1,125}[A-Za-z]{2,5}$/;
  var phreg = /^[+][0-9]{1,2}[6-9]{1}[0-9]{9}$/;

  //checking regex and displaying the warnings
  var fn = document.forms["form1"]["First"].value;
  if (freg.test(fn) == false) {
    document.getElementById("fname_msg").innerHTML = "Invalid First name ";
    flag = 1;
  }


  var ln = document.forms["form1"]["Last"].value;
  if (freg.test(ln) == false) {
    document.getElementById("lname_msg").innerHTML = "Invalid Last name ";
    flag = 1;
  }


  if (tod > dobreg || dob_given == "") {
    document.getElementById("dob_msg").innerHTML = "Incorrect date";
    flag = 1;
  }

  var em = document.forms["form1"]["email"].value;
  if (emailreg.test(em) == false) {
    document.getElementById("email_msg").innerHTML = "Incorrect email ";
    flag = 1;
  }

  var phone = document.forms["form1"]["ph"].value;
  if (phreg.test(phone) == false) {
    document.getElementById("ph_msg").innerHTML = "Invalid phone number ";
    flag = 1;
  }

  //Inserting into table if successful
  if (flag == 0) {

    var table = document.getElementById("myTable");
    var row = table.insertRow(++i);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    var cell3 = row.insertCell(2);
    var cell4 = row.insertCell(3);
    var cell5 = row.insertCell(4);

    var fnn = document.forms["form1"]["First"].value;
    fnn = fnn[0].toUpperCase() + fnn.substring(1, fnn.length);

    var lnn = document.forms["form1"]["Last"].value;
    lnn = lnn[0].toUpperCase() + lnn.substring(1, lnn.length);



    cell1.innerHTML = fnn;
    cell2.innerHTML = lnn;
    cell3.innerHTML = document.forms["form1"]["dob"].value;
    cell4.innerHTML = document.forms["form1"]["email"].value;
    cell5.innerHTML = document.forms["form1"]["ph"].value;
    document.getElementById("form1").reset();
    return true;
  }
}

// To Capitalize first letter while typing
$(document).ready(function() {
  $("#First").keyup(function() {
    $('#First').css('textTransform', 'capitalize');
  });
  $("#Last").keyup(function() {
    $('#Last').css('textTransform', 'capitalize');
  });
});