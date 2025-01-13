from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import linear_kernel
from database import connect_db
import datetime

def find_matching_jobs(user_id):
    conn = connect_db()
    cursor = conn.cursor()

    cursor.execute('SELECT skills FROM Users WHERE user_id = %s', (user_id,))
    user_skills = cursor.fetchone()[0]

    cursor.execute('SELECT job_id, required_skills,post_id FROM Jobs')
    jobs = cursor.fetchall()

    if not jobs:
        conn.commit()
        conn.close()
        return None

    tfidf = TfidfVectorizer()
    job_data = [user_skills] + [job[1] for job in jobs]
    tfidf_matrix = tfidf.fit_transform(job_data)

    cosine_sim = linear_kernel(tfidf_matrix[0:1], tfidf_matrix[1:]).flatten()

    threshold = 0.7  # Ngưỡng tương đồng (tùy chỉnh)
    for idx, similarity in enumerate(cosine_sim):
        if similarity > threshold:
            job_id = jobs[idx][0]
            cursor.execute('INSERT INTO Skill_Match (user_id, job_id) VALUES (%s, %s)',
                           (user_id, job_id))

    conn.commit()
    conn.close()


def find_matching_user(job_id):
    """Find users matching the required skills for a given job."""
    # Kết nối cơ sở dữ liệu
    conn = connect_db()
    cursor = conn.cursor()

    # Lấy thông tin về công việc, bao gồm kỹ năng yêu cầu và ngày hết hạn
    cursor.execute('SELECT required_skills, expiration_date FROM Jobs WHERE post_id = %s', (job_id,))
    job_data = cursor.fetchone()

    if not job_data:
        conn.commit()
        conn.close()
        return None  # Công việc không tồn tại

    required_skills = job_data[0]
    expiration_date = job_data[1]

    # Kiểm tra xem công việc đã hết hạn chưa
    current_date = datetime.date.today()
    if expiration_date < current_date:
        print(f"Job {job_id} has expired.")
        conn.commit()
        conn.close()
        return None  # Công việc đã hết hạn

    # Lấy danh sách tất cả ứng viên và kỹ năng của họ
    cursor.execute('SELECT user_id, skills FROM Users')
    users = cursor.fetchall()

    if not users:
        conn.commit()
        conn.close()
        return None

    # Tính toán độ tương đồng dựa trên kỹ năng của công việc và ứng viên
    tfidf = TfidfVectorizer()
    user_data = [required_skills] + [user[1] for user in users]
    tfidf_matrix = tfidf.fit_transform(user_data)

    cosine_sim = linear_kernel(tfidf_matrix[0:1], tfidf_matrix[1:]).flatten()

    threshold = 0.6 # Ngưỡng tương đồng (có thể tùy chỉnh)
    matching_users = [users[idx][0] for idx, similarity in enumerate(cosine_sim) if similarity > threshold]

    # Đóng kết nối CSDL
    conn.commit()
    conn.close()

    return matching_users
