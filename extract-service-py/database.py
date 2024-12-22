import pymysql
from mysql.connector import Error

def connect_db():
    """Establish a connection to the database."""
    try:
        connection = pymysql.connect(
            host="127.0.0.1",
            user="root",
            password="12345678",
            database="job-csm-extract-service",
            port=3306
        )
        return connection
    except Error as e:
        print(f"Error connecting to MySQL: {e}")
        return None

def create_tables():
    """Create tables in the database if they do not exist."""
    conn = connect_db()
    if conn is None:
        print("Connection failed.")
        return

    try:
        cursor = conn.cursor()
        cursor.execute(''' 
            CREATE TABLE IF NOT EXISTS Users (
                user_id VARCHAR(50) PRIMARY KEY,
                skills TEXT
            )
        ''')

        cursor.execute(''' 
            CREATE TABLE IF NOT EXISTS Jobs (
                job_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                required_skills TEXT,
                post_id VARCHAR(50),
                expiration_date DATE  -- New column for expiration date
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
    except Error as e:
        print(f"Error creating tables: {e}")
    finally:
        cursor.close()
        conn.close()

def save_user(user_id, skills):
    """Save a user into the Users table."""
    conn = connect_db()
    if conn is None:
        print("Connection failed.")
        return

    try:
        cursor = conn.cursor()
        cursor.execute('INSERT INTO Users (user_id, skills) VALUES (%s, %s)',
                       (user_id, skills))
        conn.commit()
    except Error as e:
        print(f"Error saving user: {e}")
    finally:
        cursor.close()
        conn.close()

def save_job(required_skills, post_id, expiration_date):
    """Save a job into the Jobs table, updating if post_id already exists."""
    conn = connect_db()
    if conn is None:
        print("Connection failed.")
        return

    try:
        cursor = conn.cursor()

        # Check if the post_id already exists in the Jobs table
        cursor.execute('SELECT job_id FROM Jobs WHERE post_id = %s', (post_id,))
        existing_job = cursor.fetchone()

        if existing_job:
            # If a job with the post_id exists, delete the old record
            cursor.execute('DELETE FROM Jobs WHERE post_id = %s', (post_id,))
            print(f"Deleted existing job with post_id: {post_id}")

        # Insert the new job record, including the expiration_date
        cursor.execute('INSERT INTO Jobs (required_skills, post_id, expiration_date) VALUES (%s, %s, %s)',
                       (required_skills, post_id, expiration_date))
        conn.commit()
        print(f"Inserted new job with post_id: {post_id}")

    except Error as e:
        print(f"Error saving job: {e}")
    finally:
        cursor.close()
        conn.close()

# Example usage
if __name__ == "__main__":
    create_tables()
