function changeCount(id) {
	$.ajax({
		type: 'POST',
		url: 'cart/change',
		data: `snowflakeId=${id}&count=${$('#input-cost' + id).val()}`,
		success: changeCartResult
	});
}

function changeCartResult(answer) {
	try {
		let snowflake = $.parseJSON(answer);
		$('#p-adTitle').text("Корзина. Общая сумма товаров: " + snowflake.totalPrice + " ₽");
		$('#input-cost' + snowflake.id).val(snowflake.count);
		$('#p-cost' + snowflake.id).text("Цена: " + snowflake.totalPriceOfChanged + " ₽");
	} catch (e) {
		if (answer === "redirect:auth") {
			location.href = "/auth";
		} else {
			alert(answer);
		}
	}
}

function snowflakeCard(id){
	location.href = "/snowflake/" + id;
}