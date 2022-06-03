function convertMethod(selector) {
    $(selector).click(function () {
        var action = $(this).attr('href');
        var params = $(this).data();
        var cmsg = '';
        var $form = $('<form/>');
        $form.attr('action', action);
        for (var name in params) {
            var attrName = name;
            if ("confirm_message" == name) {
                cmsg = params[name];
                continue;
            }
            if ("method" == name) {
                $form.attr('method', 'post');
                attrName = '_method';
            }
            var $input = $('<input type="hidden" />').attr('name', attrName).val(params[name]);
            $form.append($input);
        }
        if (cmsg) {
            if (window.confirm(cmsg)) {
                $('body').append($form);
                $form.submit();
            }
        } else {
            $('body').append($form);
            $form.submit();
        }
        return false;
    });
}