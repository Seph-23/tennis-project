$(document).ready(function() {
  $('#summernote').summernote({
  height : 300,
      minHeight : null,
      maxHeight : null,
      focus : true,
      callbacks : {
    onImageUpload : function(files, editor, welEditable) {
      for (var i = 0; i < files.length; i++) {
        sendFile(files[i], this);
      }
    }
  }
});
});

function sendFile(file, el) {
  var form_data = new FormData();
  form_data.append('file', file);
  $.ajax({
  data : form_data,
      type : "POST",
      url : '/image',
      cache : false,
      contentType : false,
      enctype : 'multipart/form-data',
  processData : false,
      success : function(url) {
    $(el).summernote('insertImage', url, function($image) {
      $image.css('width', "25%");
    });
  }
});
}


