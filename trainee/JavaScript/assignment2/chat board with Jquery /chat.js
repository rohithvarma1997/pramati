function valiform() {
  var ind;
  var fn=$("#name").val();
  var que=$("#question").val();
  var data=[{'name':'hemanth','color':'blue','movie':'3 idiots','place':'kodaicanal','actor':'akshay-kumar','sport':'cricket','food':'veg biryani','vehical':'bike','actress':'sonakshi','domain':'devolopment','skill':'ruby','company':'pramati','experience':'four','marital':'married'},
             {'name':'haridas narayanaswamy','color':'blue','movie':'kunfu panda','place':'kumarakam(kerala)','actor':'mamutti','sport':'cricket','food':'malabar biriyani','vehical':'car','actress':'parvathi','domain':'machine learning','skill':'ML','company':'google','experience':'Nine','marital':'married'},
             {'name':'seshachalam malisetti','color':'green','movie':'the shawshank redemption','place':'gokarana','actor':'brad pitt','sport':'cricket','food':'andhra meals','vehical':'lockheed sr-71(blackbird)','actress':'carey mulligan','domain':'distributed computing','skill':'concurrent programming','company':'spacex','experience':'six','marital':'single'},
             {'name':'anija','color':'white','movie':'manichithrathazhu','place':'kochin','actor':'mohanlal','sport':'badmintion','food':'parrota and beef','vehical':'bike','actress':'shobhana','domain':'ruby','skill':'ruby,rails,javascript','company':'ruby on rails private limited','experience':'five','marital':'married'},
             {'name':'sindu','color':'pink','movie':'mugavari','place':'swtizerland','actor':'ajith','sport':'tennis','food':'chicken','vehical':'bike','actress':'keerthi suresh','domain':'development','skill':'php','company':'pramati','experience':'3','marital':'married'},
             {'name':'rakesh','color':'white','movie':'metukudi','place':'tirunelveli','actor':'ajith','sport':'cricket','food':'fish','vehical':'bike','actress':'trisha','domain':'devops','skill':'docker','company':'pramati','experience':'nine','marital':'married'},
             {'name':'amos layola','color':'blue','movie':'lord of rings','place':'trichy','actor':'none','sport':'cricket','food':'biriyani','vehical':'car','actress':'none','domain':'data science','skill':'data science','company':'pramati','experience':'5','marital':'married'},
             {'name':'raveena','color':'orange','movie':'CIA','place':'dubai','actor':'mohanlal','sport':'cricket','food':'chicken biriyani','vehical':'bike','actress':'nazriya','domain':'development','skill':'python','company':'pramati','experience':'zero','marital':'single'},
             {'name':'ashok raja','color':'red','movie':'prediator','place':'kanya kumari','actor':'none','sport':'foot ball','food':'noodles','vehical':'bike','actress':'jacqueline','domain':'big data','skill':'data base','company':'pramati','experience':'6','marital':'single'},
             {'name':'arun','color':'blue','movie':'alaipayuthey','place':'gokarna','actor':'vjs','sport':'hockey','food':'noodles','vehical':'bicycle','actress':'simran','domain':'DC','skill':'ML','company':'verizon','experience':'12','marital':'married'}];

var e = document.getElementById("ans");
var strUser = e.options[e.selectedIndex].value;

jQuery.each(data,function(index,value){
  if(data[index].name==strUser)
  {
    ind=index;
  }
});


  if(/color/i.test(que)==true)
  {
    $("#answer").val(data[ind].name+" favorite color is "+data[ind].color);
  }

  if(/movie/i.test(que)==true)
  {
    $("#answer").val(data[ind].name+" favorite movie is "+data[ind].movie);
  }

  if(/place/i.test(que)==true)
  {
    $("#answer").val(data[ind].name+" favorite place is "+data[ind].place);
  }

  if(/actor/i.test(que)==true)
  {
    $("#answer").val(data[ind].name+" favorite actor is "+data[ind].actor);
  }

  if(/sport/i.test(que)==true)
  {
    $("#answer").val(data[ind].name+" favorite sport is "+data[ind].sport);
  }

  if(/food/i.test(que)==true)
  {
    $("#answer").val(data[ind].name+" favorite food is "+data[ind].food);
  }

  if(/vehicle/i.test(que)==true)
  {
    $("#answer").val(data[ind].name+" favorite vehicle is "+data[ind].vehicle);
  }

  if(/actress/i.test(que)==true)
  {
    $("#answer").val(data[ind].name+" favorite actress is "+data[ind].actress);
  }
  if(/domain/i.test(que)==true)
  {
    $("#answer").val(data[ind].name+" works in "+data[ind].domain +" domain");
  }
  if(/skill/i.test(que)==true)
  {
    $("#answer").val(data[ind].name+" is skilled in "+data[ind].skill);
  }
  if(/company/i.test(que)==true)
  {
    $("#answer").val(data[ind].name+" favorite company is "+data[ind].company);
  }
  if(/experience/i.test(que)==true)
  {
    $("#answer").val(data[ind].name+" has an experience of "+data[ind].experience+" years");
  }
  if(/marital/i.test(que)==true)
  {
    $("#answer").val(data[ind].name+" is "+data[ind].marital);
  }





}
