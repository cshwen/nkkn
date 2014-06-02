$('#login_tab a').click(function(e) {
	e.preventDefault()
	$(this).tab('show')
})

setTimeout('$(".login_sign ").hide("slow")',3000);

$('#books_tab a').click(function(e) {
	e.preventDefault()
	$(this).tab('show')
})