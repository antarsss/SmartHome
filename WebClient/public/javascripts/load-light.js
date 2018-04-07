$.ajax({
   url: url_home + "/device/lights",
   dataType: 'json',
   async: true,
   data: "",
}).done(function(lights) {
   setLightData(lights);
   sendLightData(lights);
   onLightData(lights);
});

function setLightData(lights) {
   if (lights.length == 0) {
      $('.light-container').html('<h3>No Device</h3>');
   } else {
      $('.light-container').html('');
      lights.forEach(function(data) {
         var checked = data.state ? "checked" : "";
         var light = '<div class="khung col-xs-6 col-sm-4 col-md-3 col-lg-2">' +
            '<div class="child-khung">' +
            '<img src="/img/light.png">' +
            '<br>' +
            '<h5>' + data.deviceName + '</h5>' +
            '<label class="switch">' +
            '<input class="light-control" type="checkbox" ' + checked + ' id="' + data._id + '" value="' + data._id + '" />' +
            '<span style="float: left;" class="slider round"></span>' +
            '</label>' +
            '</div>' +
            '</div>';
         $('.light-container').append(light);
      });
   }

};

function sendLightData(lights) {
   $('.light-control').click(function() {
      var value = $(this).val();
      var state = $(this).prop("checked") ? true : false;
      lights.forEach(function(e) {
         if (e._id == value) {
            delete e.createdAt;
            delete e.updatedAt;
            delete e.__v;
            e.state = state;
            console.log(e);
            socket.emit("c2s-ledchange", e);
         }
      })
   });
}

function onLightData(lights) {
   socket.on("s2c-ledchange", function(rs) {
      $("#" + rs._id).prop("checked", rs.state);
   });
}