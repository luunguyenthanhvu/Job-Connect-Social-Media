import pymysql

try:
    connection = pymysql.connect(
        host="127.0.0.1",
        user="root",
        password="12345678",
        database="job-csm-extract-service",
        port=3306
    )
    print("Kết nối thành công đến MySQL với pymysql!")
except pymysql.MySQLError as e:
    print("Lỗi khi kết nối đến MySQL:", e)
finally:
    if connection:
        connection.close()
        print("Đã đóng kết nối MySQL.")
