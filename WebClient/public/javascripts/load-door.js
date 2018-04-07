function loadDoor(position, container) {
   $.ajax({
      url: url_home + "/device/door/" + position,
      dataType: 'json',
      async: true,
      data: "",
   }).done(function(doors) {
      console.log(doors);
      setDoorData(doors, container);
      sendDoorData(doors);
      onDoorData(doors);
   });
}

function setDoorData(doors, container) {
   console.log(container);
   if (doors.length == 0) {
      $('.' + container).html('<h3>No Door</h3>');
   } else {
      $('.' + container).html('');
      doors.forEach(function(data) {
         var checked = data.state ? "checked" : "";
         var door = '<div class="khung col-xs-6 col-sm-4 col-md-3 col-lg-2">' +
            '<div class="child-khung">' +
            '<img src="/img/close_door.png">' +
            '<br>' +
            '<h5 class=" col-md-12">' + data.deviceName + '</h5>' +
            '<label class="switch">' +
            '<input class="door-control" type="checkbox" ' + checked + ' id="' + data._id + '" value="' + data._id + '" />' +
            '<span style="float: left;" class="slider round"></span>' +
            '</label>' +
            '</div>' +
            '</div>';
         $('.' + container).append(door);
      });
   }
};

function sendDoorData(doors) {
   $('.door-control').click(function() {
      var value = $(this).val();
      doors.forEach(function(e) {
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

function onDoorData(doors) {
   socket.on("s2c-ledchange", function(rs) {
      console.log(rs);
      $("#" + rs._id).prop("checked", rs.state);
   });
}

loadDoor("LIVINGROOM", "door-container-living");

loadDoor("BEDROOM", "door-container-bed");