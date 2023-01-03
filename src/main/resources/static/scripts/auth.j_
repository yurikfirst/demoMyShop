function authPressedEnter(e) {
	if (e.keyCode === 13) {
		bEnterClick();
	}
}

function pRegisterClick() {
	$('#in-pass-accept').show(300);
	$('#b-enter').addClass('register');
	$('#b-enter').html('Зарегистрироваться');
	$('#p-register').hide(300);
}

function bEnterClick() {
	$('body').css("cursor", "wait");
	disabledElements(true);
	$('#p-err').text('');

	let login = $('#in-login').val();
	let pass = $('#in-pass').val();
	if (!checkLogin(login) || !checkPass(pass)) {
		disabledElements(false);
		return;
	}

	if ($('#b-enter').hasClass('register')) {
		let acc_pass = $('#in-pass-accept').val();
		if (pass !== acc_pass) {
			alert('Пароль и подтверждение не совпадают');
			return;
		}

		$.ajax({
			type: 'POST',
			url: 'auth',
			data: `action=register&login=${login}&password=${getHash(pass)}`,
			success: registerAnswer
		});
	} else {
		$.ajax({
			type: 'POST',
			url: 'auth',
			data: `action=enter&login=${login}&password=${getHash(pass)}`,
			success: enterAnswer
		});
	}
}

function enterAnswer(answer) {
	if (answer === 'ok') {
		location.href = '/';
	} else {
		$('#p-err').text('Неверный логин или пароль');
	}
	disabledElements(false);
	$('body').css("cursor", "default");
}

function registerAnswer(answer) {
	if (answer === 'ok') {
		$('#b-enter').removeClass('register');
		alert("Пользователь успешно зарегистрирован.");
		bEnterClick();
	} else {
		$('#p-err').text('Логин уже используется');
	}
	disabledElements(false);
	$('body').css("cursor", "default");
}

function getHash(text) {
	return text;
}

function checkLogin(login) {
	if (login.length < 4) {
		alert('Логин должен содержать от 4 до 20 символов, может содержать латинские буквы, цифры и нижнее подчёркивание, должен начинаться с буквы и не может окнчиваться нижним подчёркиванием');
		return false;
	}
	return true;
}

function checkPass(pass) {
	if (pass.length < 8) {
		alert('Пароль должен содержать от 8 до 20 символов и не может содержать пробелов');
		return false;
	}
	return true;
}

function disabledElements(flag) {
	$("input").prop('disabled', flag);
	$("button").prop('disabled', flag);
}