var lightlist = [];
$.ajax({
   url: "http://192.168.1.5:3000/devices",
   dataType: 'json',
   async: true,
   data: "",
   success: function(data) {
      lightlist = data;
      console.log(lightlist);
   }
});