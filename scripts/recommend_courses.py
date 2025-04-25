import sys
import json
from typing import Iterable
from ai_helper import Course, RequiredCourseInfo
from load_courses import courses_from_required_course_info, get_required_courses

# Make a CSP algorithm that goes through an iterable of RequiredCourseInfo objects that still need to be taken and returns a 
#   list of courses that do not conflict with each other, prioritizing courses with lower semester_number values, trying to get 5 classes
def recommend_courses(required_courses: Iterable[RequiredCourseInfo], desired_credits: int, verbosity: int = 0) -> str | None:
    """
    Recommends a list of up to 5 courses that do not conflict with each other,
    prioritizing courses with lower semester_number values.

    Args:
        required_courses (iterable): An iterable of RequiredCourseInfo objects representing required courses not yet taken.

    Returns:
        list: A list of up to non-conflicting RequiredCourseInfo objects.
    """
    # Sort courses by semester_number (ascending)
    sorted_courses: list[RequiredCourseInfo] = sorted(required_courses, key=lambda course: course.semester_number)

    # Initialize the result list
    selected_courses: list[Course] = []

    # Run the recursive method to find non-conflicting courses for a schedule
    result_courses: list[Course] | None = _recommend_course(sorted_courses, selected_courses, desired_credits)

    # Display the resulting schedule if verbosity is greater than 0
    if verbosity > 0 and result_courses is not None:
        print(f"Recommended Schedule:\n\t{"\n\t".join([str(c) for c in result_courses])}\n")

    # Convert the result to a JSON string containing only the cids
    if result_courses is not None:
        cids = [course.cid for course in result_courses]
        return json.dumps(cids, indent=4)
    return None

def _recommend_course(sorted_required_courses_remaining: list[RequiredCourseInfo], selected_courses: list[Course], 
                      credits_remaining: int) -> list[Course] | None:
    if credits_remaining <= 0:
        return selected_courses
    # Loop through all remaining required courses
    for required_info in sorted_required_courses_remaining:
        # Get all courses for the current required course info
        for course in courses_from_required_course_info(required_info):
            # If no conflict...
            if course not in selected_courses:
                # Add course to selected courses and recurse
                result = _recommend_course(
                    sorted_required_courses_remaining = [info for info in sorted_required_courses_remaining if info != required_info], 
                    selected_courses = selected_courses + [course], 
                    credits_remaining = credits_remaining - course.credits)
                # If result is not None, return it
                if result is not None:
                    return result
                
def main(major, num_credits):
    # Call recommend courses with a list of required courses and desired credits from files
    required_courses = get_required_courses(major)
    schedule: str | None = recommend_courses(required_courses, num_credits, 0)
    print(schedule)

if __name__ == '__main__':
    major, num_credits = sys.argv[1], int(sys.argv[2])
    main(major, num_credits)


