import Swal from 'sweetalert2';

// SweetAlert 공통 설정
const showAlert = (title, text, icon = 'info') => {
  return Swal.fire({
    title,
    text,
    icon,
    confirmButtonText: '확인',
    buttonsStyling: false,
    customClass: {
      confirmButton: 'btn btn-danger',
      popup: 'custom-swal-popup',
      title: 'custom-swal-title',
      text: 'custom-swal-text',
    },
  });
};

export default showAlert;
