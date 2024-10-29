import mysql.connector

def connect_db():
    return mysql.connector.connect(
        host="localhost:3306",
        user="root",
        password="12345678",
        database="job-csm-extract-service"
    )

def create_tables():
    conn = connect_db()
    cursor = conn.cursor()

    cursor.execute('''
    CREATE TABLE IF NOT EXISTS Users (
        user_id VARCHAR(50) PRIMARY KEY,
        skills TEXT
    )
    ''')

    cursor.execute('''
    CREATE TABLE IF NOT EXISTS Jobs (
        job_id BIGINT PRIMARY KEY,
        required_skills TEXT
    )
    ''')

    cursor.execute('''
    CREATE TABLE IF NOT EXISTS Skill_Match (
        match_id INT AUTO_INCREMENT PRIMARY KEY,
        user_id VARCHAR(50),
        job_id BIGINT,
        FOREIGN KEY (user_id) REFERENCES Users (user_id),
        FOREIGN KEY (job_id) REFERENCES Jobs (job_id)
    )
    ''')

    conn.commit()
    conn.close()

def save_user(user_id, skills):
    conn = connect_db()
    cursor = conn.cursor()
    cursor.execute('INSERT INTO Users (user_id, skills) VALUES (%s, %s)',
                   (user_id, skills))
    conn.commit()
    conn.close()

def save_job(job_id, required_skills):
    conn = connect_db()
    cursor = conn.cursor()
    cursor.execute('INSERT INTO Jobs (job_id, required_skills) VALUES (%s, %s)',
                   (job_id, required_skills))
    conn.commit()
    conn.close()
