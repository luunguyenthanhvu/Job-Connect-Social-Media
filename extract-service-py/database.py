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
        return  # Exit if the connection failed

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
    cursor.close()  # Close cursor
    conn.close()    # Close connection

def save_user(user_id, skills):
    """Save a user into the Users table."""
    conn = connect_db()
    if conn is None:
        return  # Exit if the connection failed

    cursor = conn.cursor()
    cursor.execute('INSERT INTO Users (user_id, skills) VALUES (%s, %s)',
                   (user_id, skills))
    conn.commit()
    cursor.close()  # Close cursor
    conn.close()    # Close connection

def save_job(job_id, required_skills):
    """Save a job into the Jobs table."""
    conn = connect_db()
    if conn is None:
        return  # Exit if the connection failed

    cursor = conn.cursor()
    cursor.execute('INSERT INTO Jobs (job_id, required_skills) VALUES (%s, %s)',
                   (job_id, required_skills))
    conn.commit()
    cursor.close()  # Close cursor
    conn.close()    # Close connection

# Example usage
if __name__ == "__main__":
    create_tables()  # Create tables when script is run
    save_user('user123', 'Python, SQL')
    save_job(1, 'Python, Java')
