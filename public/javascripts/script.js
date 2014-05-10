$('#myTab a').click(function(e) {
	e.preventDefault()
	$(this).tab('show')
})

$('#myTab a[href="#register"]').tab('show');
$('#myTab a[href="#login"]').tab('show');

setTimeout('$(".alert-error").hide("slow")',3000);
setTimeout('$(".success").hide("slow")',3000);
