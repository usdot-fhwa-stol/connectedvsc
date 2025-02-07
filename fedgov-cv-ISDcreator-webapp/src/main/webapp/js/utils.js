
function getCookie(cname) {
  var name = cname + "=";
  var decodedCookie = decodeURIComponent(document.cookie);
  var ca = decodedCookie.split(';');
  for(var i = 0; i <ca.length; i++) {
      var c = ca[i];
      while (c.charAt(0) == ' ') {
          c = c.substring(1);
      }
      if (c.indexOf(name) == 0) {
          return c.substring(name.length, c.length);
      }
  }
  return "";
}

function isOdd(num) { return (num % 2) == 1;}


 /***
  * @brief Show and hide RGA related fields. 
  * Note: extra RGA fields in addition to MAP message should only appear at the "Reference Point" dialog.
  * @param {type} Boolean show or hide RGA fields
  */
 function show_rga_fields(hide=true){
  if(hide){        
     $(".extra_rga_field").hide();
  }else{        
     $(".extra_rga_field").show();
  }
}

function enable_rga_fields(enable=true){    
  if(enable){        
       $(".extra_rga_field_input").prop('disabled', false);
  }else{     
       $(".extra_rga_field_input").prop('disabled', true);
  }
  add_rga_fields_validation(enable);
}

function add_rga_fields_validation(enable=true){
  if(enable){
    $("input:text.required").attr('data-parsley-required', true);
  }else{
    $("input:text.required").attr('data-parsley-required', false);
  }

}

export {
  getCookie,
  isOdd,
  enable_rga_fields,
  add_rga_fields_validation
}