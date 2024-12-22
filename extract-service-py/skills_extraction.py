from sklearn.feature_extraction.text import TfidfVectorizer
from database import save_user, save_job
import string
from nltk.tokenize import word_tokenize
from nltk.corpus import stopwords
from nltk.stem import WordNetLemmatizer
from gensim import corpora
from gensim.models.ldamodel import LdaModel
from bs4 import BeautifulSoup

# Tạo tập hợp các stop words
stop_words = set(stopwords.words('english'))
lemmatizer = WordNetLemmatizer()

def remove_html(text):
    soup = BeautifulSoup(text, "html.parser")
    return soup.get_text()

def preprocess(text):
    text = remove_html(text)
    tokens = word_tokenize(text.lower())
    tokens = [word for word in tokens if word not in string.punctuation]
    tokens = [lemmatizer.lemmatize(word) for word in tokens if word not in stop_words]
    return tokens

def extract_keywords(text, model, dictionary, num_keywords=10):
    tokens = preprocess(text)
    bow = dictionary.doc2bow(tokens)
    topics = model.get_document_topics(bow)

    keywords = set()
    for topic_id, topic_prob in sorted(topics, key=lambda x: -x[1])[:num_keywords]:
        top_words = model.show_topic(topic_id, topn=num_keywords)
        for word, prob in top_words:
            if len(word) > 3 and word not in stop_words:
                keywords.add(word)

    return list(keywords)

def extract_and_save_skills_user(user_id, cv_text, model=None, dictionary=None):
    if model is None or dictionary is None:
        # Load default model if not provided
        model = LdaModel.load("C:\\Users\\PC\\Desktop\\Models\\model_lda_100.model")
        dictionary = corpora.Dictionary.load("C:\\Users\\PC\\Desktop\\Models\\model_lda_100.model.id2word")

    extracted_skills = extract_keywords(cv_text, model, dictionary)
    save_user(user_id, ', '.join(extracted_skills))

def extract_and_save_skills_job(job_id, job_description,expirationDate, model=None, dictionary=None):
    if model is None or dictionary is None:
        # Load default model if not provided
        model = LdaModel.load("C:\\Users\\PC\\Desktop\\Models\\model_lda_100.model")
        dictionary = corpora.Dictionary.load("C:\\Users\\PC\\Desktop\\Models\\model_lda_100.model.id2word")

    extracted_skills = extract_keywords(job_description, model, dictionary)
    save_job(', '.join(extracted_skills),job_id, expirationDate)