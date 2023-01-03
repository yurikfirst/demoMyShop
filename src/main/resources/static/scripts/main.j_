function filterCategory(category) {
	location.href = "/?category=" + category;
	// $.ajax({
	// 	type: 'GET',
	// 	url: '',
	// 	data: `category=${category}`,
	// 	success: updateSnowflakes
	// });
}

function updateSnowflakes() {
	$('').replaceAll('main');
}

function actionToCart(id) {
	$.ajax({
		type: 'POST',
		url: 'cart',
		data: `snowflakeId=${id}`,
		success: actionCartResult
	});
}

function actionCartResult(answer) {
	try {
		let snowflake = $.parseJSON(answer);
		$('.div-goods').each(function (index, element) {
			if ($(element).attr("data-snowflakeId") === snowflake.id) {
				$(element).attr("data-inCart", snowflake.isInCart);
				// return false;
			}
		})
		updateStatusGoodsInCart(snowflake.id);
	} catch (e) {
		if (answer === "redirect:auth") {
			location.href = "/auth";
		} else {
			alert(answer);
		}
	}
}

function updateStatusGoodsInCart(snowflakeId) {
	$('.div-goods-cart').each(function (index, element) {
		if ($(element).parent().attr("data-inCart") === "true") {
			$(element).css("background-color", "darkgreen");
		} else {
			$(element).css("background-color", "cadetblue");
		}
	});
}

function snowflakeCard(id) {
	location.href = "/snowflake/" + id;
}