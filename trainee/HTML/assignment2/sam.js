
   $( function() {
    $( "#dob" ).datepicker();
    });



     function validateForm() {

      document.getElementById("fname_msg").innerHTML="";
      document.getElementById("lname_msg").innerHTML="";
      document.getElementById("dob_msg").innerHTML="";
      document.getElementById("ph_msg").innerHTML="";
      document.getElementById("email_msg").innerHTML="";


      var i=0;
      var flag=0;
      var freg=/^[a-zA-Z]{3,20}$/;
      var lreg=/^[a-zA-Z]{3,20}$/;
      var dobreg=new Date();
      var tod=new Date(document.getElementById("dob").value);
      var emailreg=/^[A-Z0-9a-z]+@[A-Za-z0-9]+.[A-Za-z]{2,4}$/;
      var phreg=/^[6-9]{1}[0-9]{9}$/;

      var fn = document.forms["form1"]["First"].value;
      if (freg.test(fn) == false){
        document.getElementById("fname_msg").innerHTML="This is invalid name ";
        flag=1;
      }


      var ln = document.forms["form1"]["Last"].value;
      if (freg.test(ln) == false) {
          document.getElementById("lname_msg").innerHTML="This is invalid name ";
          flag=1;
      }

       
      if (tod > dobreg) {
          document.getElementById("dob_msg").innerHTML="please fill correct the date ";
          flag=1;
      }

      var em = document.forms["form1"]["email"].value;
      if (emailreg.test(em) == false) {
          document.getElementById("email_msg").innerHTML="This is incorrect email ";
          flag=1;
      }

      var phone = document.forms["form1"]["ph"].value;
      if (phreg.test(phone) == false) {
        document.getElementById("ph_msg").innerHTML="This is invalid phone number ";
        flag=1;
          
      }
      if(flag==0)
      {

      var table = document.getElementById("myTable");
      var row = table.insertRow(++i);
      var cell1 = row.insertCell(0);
      var cell2 = row.insertCell(1);
      var cell3 = row.insertCell(2);
      var cell4 = row.insertCell(3);
      var cell5 = row.insertCell(4);
      
      cell1.innerHTML = document.forms["form1"]["First"].value;
      cell2.innerHTML = document.forms["form1"]["Last"].value;
      cell3.innerHTML = document.forms["form1"]["dob"].value;
      cell4.innerHTML = document.forms["form1"]["email"].value;
      cell5.innerHTML = document.forms["form1"]["ph"].value;
      document.getElementById("form1").reset();
     return true;    
    }
  }

    $(document).ready(function(){
    $("#First").keyup(function () {  
                $('#First').css('textTransform', 'capitalize');  
            });
    $("#Last").keyup(function () {  
                $('#Last').css('textTransform', 'capitalize');  
            });
    });

