# Android Java Bus Reservation

Final Project 4 - Hacktiv8

Kelompok 8
- Faaris Muda Dwi Nugraha
- Fidela Putri Arifianti
- Nur Mohamad Iqbal Jauhari

## Cara Menjalankan Project
1. Clone repository dan buka project ini di Android Studio
2. Install [XAMPP](https://www.apachefriends.org/download.html), kemudian jalankan Apache dan MySQL
3. Download dan ekstrak file [bus_ticket_booking](https://minhaskamal.github.io/DownGit/#/home?url=https://github.com/faarismuda/FinPro4_K8/tree/master/bus_ticket_booking)
4. Copy folder `bus_ticket_booking` dan paste ke dalam `C:\XAMPP\htdocs`
5. Buka `http://localhost/phpmyadmin/` di browser
6. Kemudian klik "New" di tab database untuk membuat database
7. Buat database baru dengan nama "bus_ticket_book" dan selanjutnya klik tab import
8. Klik browse file dan pilih "bus_ticket_book.sql" yang ada pada folder `C:\XAMPP\htdocs\bus_ticket_booking`, kemudian klik go
9. Di Android Studio cari file `Constants.java`, file tersebut dapat ditemukan di `java/com/k8/finalproject4_kelompok8/utils/Constants.java`
10. Ganti base URL dengan IP Address yang sedang digunakan
11. Buka `http://localhost/bus_ticket_booking/admin/` di browser untuk mengakses Admin Panel

## Cara Mengetahui IP Address yang Sedang Digunakan
1. Buka CMD
2. Ketik `ipconfig` kemudian tekan Enter
3. IP Address akan tampil pada baris IPv4 Address

## Informasi Login
Admin:
- Email: admin@gmail.com
- Password: admin12345

User:
- Email: faaris@upi.edu
- Password: user1234
