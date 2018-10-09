//datepicker function
$(function() {
  $(".dob").datepicker({
    format: 'dd-mm-yyyy',
    endDate: '-1d',
    autoclose: true
  })
});
//flag variables for the form fields
var nameflag=0;
var emailflag=0;
var dobflag=0;
var phoneflag=0;
//firstname validation
function namevalidation()
{
  $(".name").blur("input",function(){
    var name=$(this).val();
    var ref=$(this).parent().parent();
    var f=$(".error");
    if(name.length>=1)
    {
      $(ref).find(f).hide();
      var namereg=/[^-\s][a-zA-Z_\s-]{2,20}$/;
      if(!namereg.test(name)){
        $(ref).find(f).text("no special characters and length >3 and <20 ").show();
        nameflag=0;
      }
      else{
        $(ref).find(f).hide();
        nameflag=1;
      }
    }
  });
}
//email validation
function emailvalidation()
{
  $(".email").blur("input",function(){
    var emailval=$(this).val();
    var ref=$(this).parent().parent();
    var f=$(".error");
    if(emailval.length>0)
    {
      $(ref).find(f).hide();
      var emailreg= /^[A-Za-z0-9][A-Za-z0-9._%+-]{0,63}@(?:[A-Za-z0-9-]{1,5}\.){1,125}[A-Za-z]{2,5}$/;
      if(!emailreg.test(emailval)){
        $(ref).find(f).text("please enter corect email").show();
        emailflag=0;
      }
      else{
        $(ref).find(f).hide();
        emailflag=1;
      }
    }
  });
}
//phone number validation
function phonevalidation()
{
  $(".ph").blur("input",function(){
    var phval=$(this).val();
    var ref=$(this).parent().parent();
    var f=$(".error");
    if(phval.length>0)
    {
      $(ref).find(f).hide();
      var phreg= /^[6-9]{1}[0-9]{9}$/;
      if(!phreg.test(phval)){
        $(ref).find(f).text("invalid phone number").show();
        phoneflag=0;
      }
      else{
        $(ref).find(f).hide();
        phoneflag=1;
      }
    }
  });
}
//date of birth validation
function dobvalidation()
{
  $(document).on("change","input.dob",function(){
    var dobval=$(".dob").val();
    var ref=$(".dob").parent().parent();
    var f=$(".error");
    var dobreg=/^[0-9]{2}-[0-9]{2}-[0-9]{4}$/;
    if(!dobreg.test(dobval))
    {
        $(ref).find(f).text("invalid date").show();
        dobflag=0;
      }
      else{
        $(ref).find(f).hide();
        dobflag=1;
      }
     });
}
//function for blank fields
function blankmsg()
{
  $("input").blur(function(){
    var value=$(this).val();
    if(value.length<1){
    $(this).parent().parent().find($(".error")).text("This field is required").show();
    }
    else{
    $(this).parent().parent().find($(".error")).hide();  
    }
  });
}
//function to validatre when submit is pressed
function finalValidation()
{
  if(!nameflag){
    var ref=$(".name").parent().parent();
    var f=$(".error");
    $(ref).find(f).text("please correct the name").show();
  }
  if(!emailflag){
    var ref=$(".email").parent().parent();
    var f=$(".error");
    $(ref).find(f).text("invalid email").show();
  }
  if(!dobflag){
    var ref=$(".dob").parent().parent();
    var f=$(".error");
    $(ref).find(f).text("invalid dob").show();
  }
  if(!phoneflag){
    var ref=$(".ph").parent().parent();
    var f=$(".error");
    $(ref).find(f).text("invalid phone number").show();
  }
  if(nameflag && dobflag && phoneflag && emailflag)
  {
    var values=new Array();
    var table = $("#myTable");
    $("form#form1 input").each(function(i){
      values[i]=$(this).val(); // inserting each input value to array
    }); 
    table.append("<tr class='text-justify'><td >"+values[0]+ "</td><td >"+values[1]+"</td><td >"+values[2]+"</td><td >"+values[3]+"</td><td>"+values[4]+"</td></tr>");
    document.getElementById("form1").reset();
    values=[]; // resetting array
    // resetting the flags
    nameflag=0;
    dobflag=0;
    phoneflag=0;
    emailflag=0;
  }
}
//ready function to call all functions on page load
$(document).ready(function() {
  document.getElementById("form1").reset();
  blankmsg();
  namevalidation();
  emailvalidation();
  phonevalidation();
  dobvalidation();
  // To Capitalize first letter while typing
  $(".name").keyup(function(){
    $('.name').css('textTransform', 'capitalize');
  });
});