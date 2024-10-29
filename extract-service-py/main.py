from flask import Flask, request, jsonify
from skills_extraction import extract_and_save_skills_user, extract_and_save_skills_job
from job_matching import find_matching_jobs, find_matching_user
from database import create_tables

app = Flask(__name__)

# Tạo bảng cơ sở dữ liệu
create_tables()

@app.route('/upload_cv', methods=['POST'])
def upload_cv():
    data = request.get_json()
    user_id = data.get("user_id")
    cv_text = data.get("cv_text")

    # Gọi hàm để trích xuất và lưu kỹ năng
    extract_and_save_skills_user(user_id, cv_text)

    return jsonify({"message": "Skills extracted and saved successfully!"}), 200

@app.route('/recommend_jobs', methods=['POST'])
def recommend_jobs():
    data = request.get_json()
    user_id = data.get("user_id")

    find_matching_jobs(user_id)

    return jsonify({"message": "Job matching completed successfully!"}), 200

@app.route('/api/jobs', methods=['POST'])
def handle_job():
    # Lấy dữ liệu từ yêu cầu
    data = request.get_json()
    job_id = data.get('jobId')
    job_description = data.get('jobDescription')

    # Gọi hàm để trích xuất và lưu kỹ năng
    extract_and_save_skills_job(job_id, job_description)
    matching_users = find_matching_user(job_id)

    # Trả về danh sách các ứng viên phù hợp
    return jsonify({
        "job_id": job_id,
        "matching_users": matching_users
    })

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8084)
