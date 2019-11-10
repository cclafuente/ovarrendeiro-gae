/*
 * jquery-gallery 0.2 (25-11-2007)
 *
 * Copyright (c) 2007 Queli Coto (http://www.noth.es)
 * Lincencia bajo CC
 * Built upon jQuery 1.2 (http://jquery.com)
 */


$(document).ready(crearGaleria);

function crearGaleria ()
{
	//cargamos el title y la descripcion de la primera imagen
	var title = $(".slideShowGallery dt img").attr('title');
	//conseguimos el ancho y la altura de nuestra galerçia
	//var height = $(".slideShowGallery dt img").height();
	//var width = $(".slideShowGallery dt img").width();
	//$(".slideShowGallery dt").css({ width: width-10, height: height });
	var descripcion = $(".slideShowGallery dt img").attr('alt');
	$(".slideShowGallery dt").append("<p><strong>"+ title+"</strong> "+ descripcion +"</b>");
  	$(".slideShowGallery dt p").hide();
  	$(".slideShowGallery dt p").fadeIn('slow');
  	
	//inicializamos los click en las imagenes
	$(".slideShowGallery dd img").click( function() { 
		var urlImagen =$(this).parent().attr('href');
		var urlImagenBig =$(this).parent().attr('rev');
		var title = $(this).attr('title');
		var descripcion = $(this).attr('alt');
		ocultarFoto(urlImagen,urlImagenBig, title, descripcion);
		return false; //cancelamos el click en el a 
	} );
}

function ocultarFoto(url,urlBig,title,descripcion)
{
	   $(".slideShowGallery dt p").fadeOut();
       $(".slideShowGallery dt img").animate({ 
		  left: 50, opacity: 'hide' 
		}, 1000, function() {
			
			 var i = new Image();
	        i.src = url;
	        i.onload =  function() { 
		   $(".slideShowGallery dt img").attr('src',url);
          $(".slideShowGallery dt img").animate({ 
			  left: 50, opacity: 'show' , src : url
		  }, 1000,function() {
		  	$(".slideShowGallery dt a").attr('href',urlBig);
		  	$(".slideShowGallery dt a").attr('title',title+" / "+ descripcion);
		  	
		  	$(".slideShowGallery dt").append("<p><strong>"+ title+"</strong> "+ descripcion +"</b>");
		  	$(".slideShowGallery dt p").hide();
		  	$(".slideShowGallery dt p").fadeIn('slow');
		  	$(".slideShowGallery dt p").css('opacity',0.6);
			 } );
		  };
		 
      });
}



