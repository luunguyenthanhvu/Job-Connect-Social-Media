from flask import Flask, request, jsonify
from skills_extraction import extract_and_save_skills_user, extract_and_save_skills_job
from job_matching import find_matching_jobs, find_matching_user
from database import create_tables
from datetime import datetime
app = Flask(__name__)

# # Tạo bảng cơ sở dữ liệu
create_tables()

@app.route('/extract_user_skill', methods=['POST'])
def upload_cv():
    data = request.get_json()
    user_id = data.get("userId")
    cv_text = data.get("cvSkill")
    #
    extract_and_save_skills_user(user_id, cv_text)
    # Gọi hàm để trích xuất và lưu kỹ năng
    return '', 200

@app.route('/extract_description', methods=['POST'])
def handle_job():
    # Lấy dữ liệu từ yêu cầu
    data = request.get_json()
    job_id = data.get('jobId')
    job_description = data.get('jobDescription')
    expiration_date = data.get('expirationDate')
    expiration_date_seconds = expiration_date / 1000  # Giả sử expiration_date là timestamp mili giây
    expiration_date_obj = datetime.fromtimestamp(expiration_date_seconds)
    expiration_date_str = expiration_date_obj.strftime('%Y-%m-%d %H:%M:%S')
    # # Gọi hàm để trích xuất và lưu kỹ năng
    extract_and_save_skills_job(job_id, job_description,  expiration_date_str)
    matching_users = find_matching_user(job_id)
    print(matching_users)
    if not matching_users:
        return jsonify({
            "jobId": job_id,
            "matchingUsers": []
        })

    # Trả về danh sách các ứng viên phù hợp
    return jsonify({
        "jobId": job_id,
        "matchingUsers": matching_users
    })

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8090)
