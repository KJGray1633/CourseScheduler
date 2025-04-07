import json
from pathlib import Path
from datetime import time
from typing import Iterable
from ai_helper import Course, MeetingTime, RequiredCourseInfo

# Create global all_courses variable to store all courses (needs diff. name)
all_courses: list[Course]

def courses_from_cids(cids: Iterable[int]) -> Iterable[Course]:
    all_courses = parse_json() if all_courses is None else all_courses
    for course in all_courses:
        if course.id in cids:
            yield course

def get_required_courses(major: str) -> Iterable[RequiredCourseInfo]:
    # TODO: Update so this function queries from the database
    # Open the required courses file
    with open("required_courses.txt", "r") as file:
        # Each line contains department and then course code (i.e. "COMP 141") so pass in as RequiredCourse arguments
        for line in file:
            yield RequiredCourseInfo(*line.strip().split(""))

def get_taken_cids() -> Iterable[int]:
    # TODO: Update so this function queries from the database
    # Open the taken courses file
    with open("taken_cids.txt", "r") as file:
        # Each line contains a course ID, so we can convert it to an integer
        for line in file:
            yield int(line.strip())

def get_untaken_required_courses(major: str) -> Iterable[RequiredCourseInfo]:
    # Get the taken courses
    taken_courses: list[Course] = list(courses_from_cids(get_taken_cids()))
    # Get the required courses for the major that are not taken
    required_courses: Iterable[RequiredCourseInfo] = (
        req_course for req_course in get_required_courses(major) if any(
            req_course.course_code == taken_course.course_code and
            req_course.department == taken_course.department
            for taken_course in taken_courses
        )
    )
    return required_courses

def parse_json(file_path: str = "data_wolfe.json") -> list[Course]:
    courses = []
    try:
        # Read the JSON file
        content = Path(file_path).read_text()
        data = json.loads(content)
    except Exception as e:
        print(f"Error reading JSON file: {e}")
        return None

    # Parse the JSON data
    classes = data.get("classes", [])
    for id, c in enumerate(classes):
        curr_course = create_course_from_json(c, id)
        courses.append(curr_course)

    return courses

def create_course_from_json(c: dict, id: int) -> Course:
    curr_course = Course(id)
    curr_course.credits = c["credits"]

    for faculty in c["faculty"]:
        curr_course.add_professor(faculty.lower())

    curr_course.is_lab = c["is_lab"]
    curr_course.is_open = c["is_open"]
    curr_course.location = c["location"].lower()
    curr_course.name = c["name"].lower()
    curr_course.course_code = c["number"]
    curr_course.open_seats = c["open_seats"]
    curr_course.section = c["section"].lower()
    curr_course.semester = c["semester"].lower()
    curr_course.department = c["subject"].lower()

    for time_entry in c["times"]:
        day = time_entry["day"]
        start_time = time.fromisoformat(time_entry["start_time"])
        end_time = time.fromisoformat(time_entry["end_time"])
        meeting_time = MeetingTime(start_time, end_time, day)
        curr_course.add_time(meeting_time)

    curr_course.total_seats = c["total_seats"]
    return curr_course

def main():
    courses: list[Course] = parse_json()
    # print last five courses
    print(len(courses))
    any(print(course) for course in courses[-5:])

if __name__ == "__main__":
    main()