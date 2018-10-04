$(function() {
  $("#dob").datepicker({
    format: 'dd-mm-yyyy',
    endDate: '-1d',
    autoclose: true
  })
});

//variables to validate the form before submission
var formValid = {
  firstname: false, 
  lastname: false,
  email:false,
  dob:false,
  phnumber:false
  };


  function checkValidation() {
  if (formValid.firstname && formValid.lastname && formValid.email && formValid.dob && formValid.phnumber  ) {
    $("#sub").attr("disabled", false); // Allow submitting of form
  } else {
    $("#sub").attr("disabled", true); // Block form from being submitted
  }
  }

//function to insert the values into table
  function finalValidation()
  {
    checkValidation();//to check every field is valid and disable the submit button if not

    if (formValid.firstname && formValid.lastname && formValid.email && formValid.dob && formValid.phnumber  ) {
    var table = $("#myTable");
    var firstname=$("#First").val();
    var lastname=$("#Last").val();
    var emailval=$("#email").val();
    var phval=$("#ph").val();
    var dobval=$("#dob").val();
    table.append("<tr class='text-justify'><td >"+firstname + "</td><td >"+lastname+"</td><td >"+emailval+"</td><td >"+phval+"</td><td>"+dobval+"</td></tr>");
    document.getElementById("form1").reset();
  }
  //resetting flag values to false to run again without reloading
  formValid.firstname=false;
  formValid.lastname=false;
  formValid.email=false;
  formValid.dob=false;
  formValid.phnumber=false;
  }


// To Capitalize first letter while typing
$(document).ready(function() {
  document.getElementById("form1").reset();


  $("#First").keyup(function() {
    $('#First').css('textTransform', 'capitalize');
  });
  $("#Last").keyup(function() {
    $('#Last').css('textTransform', 'capitalize');
  });


//firstname validation
  $("#First").blur("input",function(){
    var firstname=$("#First").val();

    function msg(body)
    {
      $("#fname_msg").text(body).show();
    };    
    if(firstname.length < 1)
    {
      msg("please enter first name").show();
      formValid.firstname=false;
      checkValidation();
    }
    else
    {
      $("#fname_msg").hide();
      formValid.firstname = true;
      checkValidation();
      var firstnamereg=/^[a-zA-Z\s]{3,20}$/;
      if(!firstnamereg.test(firstname)){
        msg("no special characters allowed and length should be 3-20");
        formValid.firstname=false;
        checkValidation();
      }
      else{
        $("#fname_msg").hide();
        formValid.firstname=true;
        checkValidation();
      }
    }
     });

//lastname validation
  $("#Last").blur("input",function(){
    var lastname=$("#Last").val();

    function msg(body)
    {
      $("#lname_msg").text(body).show();
    };    
    if(lastname.length<1)
    {
      msg("please enter last name").show();
      formValid.lastname=false;
      checkValidation();
    }
    else
    {
      $("#lname_msg").hide();
      formValid.lastname = true;
      checkValidation();
      var lastnamereg=/^[a-zA-Z\s]{3,20}$/;
      if(!lastnamereg.test(lastname)){
        msg("no special characters allowed and length should be 3-20");
        formValid.lastname=false;
        checkValidation();
      }
      else{
        $("#lname_msg").hide();
        formValid.lastname=true;
        checkValidation();
      }
    }
     });

//email validation
  $("#email").blur("input",function(){
    var emailval=$("#email").val();

    function msg(body)
    {
      $("#email_msg").text(body).show();
    };    
    if(emailval.length < 1)
    {
      msg("please enter email").show();
      formValid.email=false;
      checkValidation();
    }
    else
    {
      $("#email_msg").hide();
      formValid.email = true;
      checkValidation();
      var emailreg= /^[A-Za-z0-9][A-Za-z0-9._%+-]{0,63}@(?:[A-Za-z0-9-]{1,5}\.){1,125}[A-Za-z]{2,5}$/;
      if(!emailreg.test(emailval)){
        msg("invalid email");
        formValid.email=false;
        checkValidation();
      }
      else{
        $("#emial_msg").hide();
        formValid.email=true;
        checkValidation();
      }
    }
     });

//phone number validation
  $("#ph").blur("input",function(){
    var phval=$("#ph").val();

    function msg(body)
    {
      $("#ph_msg").text(body).show();
    };    
    if(phval.length < 1)
    {
      msg("please enter phone number").show();
      formValid.phnumber=false;
      checkValidation();
    }
    else
    {
      $("#ph_msg").hide();
      formValid.phnumber = true;
      checkValidation();
      var phreg= /^[6-9]{1}[0-9]{9}$/;
      if(!phreg.test(phval)){
        msg("invalid phone number");
        formValid.phnumber=false;
        checkValidation();
      }
      else{
        $("#ph_msg").hide();
        formValid.phnumber=true;
        checkValidation();
      }
    }
     });

//date of birth validation
  $("#dob").blur("input",function(){
    var dobval=$("#dob").val();

    function msg(body)
    {
      $("#dob_msg").text(body).show();
    };
    var dobreg=/^[0-9]{2}-[0-9]{2}-[0-9]{4}$/;
    if(!dobreg.test(dobval)){
        msg("invalid date");
        formValid.dob=false;
        checkValidation();
      }
      else{
        $("#dob_msg").hide();
        formValid.dob=true;
        checkValidation();
      }

     });

});