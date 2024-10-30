from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import linear_kernel
from database import connect_db

def find_matching_jobs(user_id):
    conn = connect_db()
    cursor = conn.cursor()

    cursor.execute('SELECT skills FROM Users WHERE user_id = %s', (user_id,))
    user_skills = cursor.fetchone()[0]

    cursor.execute('SELECT job_id, required_skills FROM Jobs')
    jobs = cursor.fetchall()

    tfidf = TfidfVectorizer()
    job_data = [user_skills] + [job[1] for job in jobs]
    tfidf_matrix = tfidf.fit_transform(job_data)

    cosine_sim = linear_kernel(tfidf_matrix[0:1], tfidf_matrix[1:]).flatten()

    threshold = 0.2  # Ngưỡng tương đồng (tùy chỉnh)
    for idx, similarity in enumerate(cosine_sim):
        if similarity > threshold:
            job_id = jobs[idx][0]
            cursor.execute('INSERT INTO Skill_Match (user_id, job_id) VALUES (%s, %s)',
                           (user_id, job_id))

    conn.commit()
    conn.close()

def find_matching_user(job_id):

    # Kết nối cơ sở dữ liệu và lưu công việc
    conn = connect_db()
    cursor = conn.cursor()

    cursor.execute('SELECT required_skills FROM Jobs WHERE job_id = %s', (job_id,))
    required_skills =  cursor.fetchone()[0]

    # Lấy danh sách tất cả ứng viên và kỹ năng của họ
    cursor.execute('SELECT user_id, skills FROM Users')
    users = cursor.fetchall()

    # Tính toán độ tương đồng dựa trên kỹ năng của công việc và ứng viên
    tfidf = TfidfVectorizer()
    user_data = [required_skills] + [user[1] for user in users]
    tfidf_matrix = tfidf.fit_transform(user_data)

    cosine_sim = linear_kernel(tfidf_matrix[0:1], tfidf_matrix[1:]).flatten()

    threshold = 0.2  # Ngưỡng tương đồng (có thể tùy chỉnh)
    matching_users = [users[idx][0] for idx, similarity in enumerate(cosine_sim) if similarity > threshold]

    # Đóng kết nối CSDL
    conn.commit()
    conn.close()

    return matching_users
