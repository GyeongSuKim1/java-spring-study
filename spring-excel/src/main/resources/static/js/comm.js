// $(function () {
//     $("#datePicker").datepicker({
//         dateFormat: "yy-mm-dd",
//         maxDate: 0,
//         minDate: new Date('2018-01-01')
//     });
// });

$(function () {
    $("#datePicker").monthpicker({
        dateFormat: "yy-mm",
        maxDate: 0,
        minDate: "-3Y",
        monthNames: ['','','','','','','','','','','',''],
        monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
        // showOn: "button",
        // buttonImage: "📅",
        // buttonImageOnly: true,
        // changeYear: false,
        // yearRange: 'c-2:c+2',
    });
});