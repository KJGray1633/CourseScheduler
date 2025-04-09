from typing import Iterable
from ai_helper import Course, RequiredCourseInfo

# Make a CSP algorithm that goes through an iterable of RequiredCourseInfo objects that still need to be taken and returns a 
#   list of courses that do not conflict with each other, prioritizing courses with lower semester_number values, trying to get 5 classes
def recommend_courses(required_courses: Iterable[RequiredCourseInfo], all_courses: list[Course]) -> list[RequiredCourseInfo]:
    """
    Recommends a list of up to 5 courses that do not conflict with each other,
    prioritizing courses with lower semester_number values.

    Args:
        required_courses (iterable): An iterable of RequiredCourseInfo objects.

    Returns:
        list: A list of up to 5 non-conflicting RequiredCourseInfo objects.
    """
    # Sort courses by semester_number (ascending)
    sorted_courses: list[RequiredCourseInfo] = sorted(required_courses, key=lambda course: course.semester_number)

    # Initialize the result list
    selected_courses: list[Course] = []

    # Check for conflicts and select up to 5 courses
    for course in sorted_courses:
        if len(selected_courses) >= 5:
            break

        # Check if the course conflicts with any already selected course
        conflict = False
        for selected_course in selected_courses:
            if course.is_overlap(selected_course):
                conflict = True
                break

        # If no conflict, add the course to the selected list
        if not conflict:
            selected_courses.append(course)

    return selected_courses