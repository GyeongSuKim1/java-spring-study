// const BASE_URL = '127.0.0.1:8081/'
//
// const date = document.getElementById("datePicker")
//
// $(document).ready(function () {
//     $('#downloadBtn').click(function () {
//         $.ajax({
//             type: 'GET',
//             url: `${BASE_URL}user/excel/create`,
//             date: {date: date},
//             success: function (response) {
//                 link.href = response.fileUrl;
//                 link.download = 'Article.xlsx';
//                 document.body.appendChild(link);
//                 link.click();
//                 document.body.removeChild(link);
//             },
//             error: function(error) {
//                 console.log('Error:', error);
//             }
//         });
//     });
// });