$(document).ready(function() {

   control_menu();
   $(window).resize(function() {
      control_menu();
   })
})

function control_menu() {
   var divPa = $(".menu").parent();
   var button = $(".float-button-menu");
   button.click(function(e) {
      divPa.fadeToggle();
   })
   $(".menu").width(divPa.width())
   if ($(window).width() <= 800) {
      divPa.hide();
      button.show();
   } else {
      divPa.show();
      button.hide();
   }
}