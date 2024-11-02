import mysql.connector
from mysql.connector import Error

def connect_db():
    """Establish a connection to the database."""
    try:
        connection = mysql.connector.connect(
            host="localhost",
            port=3306,
            user="root",
            password="12345678",
            database="job-csm-extract-service"
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

def save_job(job_id, required_skills):
    """Save a job into the Jobs table."""
    conn = connect_db()
    if conn is None:
        print("Connection failed.")
        return

    try:
        cursor = conn.cursor()
        cursor.execute('INSERT INTO Jobs (job_id, required_skills) VALUES (%s, %s)',
                       (job_id, required_skills))
        conn.commit()
    except Error as e:
        print(f"Error saving job: {e}")
    finally:
        cursor.close()
        conn.close()

# Example usage
if __name__ == "__main__":
    create_tables()

