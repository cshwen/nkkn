$('#myTab a').click(function(e) {
	e.preventDefault()
	$(this).tab('show')
})

$('#myTab a[href="#register"]').tab('show');
$('#myTab a[href="#login"]').tab('show');

setTimeout('$(".login_sign ").hide("slow")',3000);