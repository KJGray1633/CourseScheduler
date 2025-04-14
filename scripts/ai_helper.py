from __future__ import annotations
from datetime import time
from typing import Iterable

class MeetingTime:
    def __init__(self, start_time, end_time, day):
        self.start_time = start_time
        self.end_time = end_time
        self.day = day

class RequiredCourseInfo:
    def __init__(self, department, course_code, semester_num):
        self.department = department
        self.course_code: int = course_code
        self.semester_number: int = int(semester_num)

    def __eq__(self, value: object) -> bool:
        if not isinstance(value, RequiredCourseInfo):
            return False
        return self.department == value.department and self.course_code == value.course_code\
            and self.semester_number == value.semester_number

class Course:
    def __init__(self, id):
        self.cid = id
        self._credits = 0
        self._professors = []
        self._is_lab = False
        self._is_open = False
        self._location = ""
        self._name = ""
        self._course_code = 0
        self._open_seats = 0
        self._section = ""
        self._semester = ""
        self._department = ""
        self._times: list[MeetingTime] = []
        self._total_seats = 0

    @property
    def credits(self):
        return self._credits

    @credits.setter
    def credits(self, value):
        self._credits = value

    @property
    def professors(self):
        return self._professors

    def add_professor(self, professor):
        self._professors.append(professor)

    @property
    def is_lab(self):
        return self._is_lab

    @is_lab.setter
    def is_lab(self, value):
        self._is_lab = value

    @property
    def is_open(self):
        return self._is_open

    @is_open.setter
    def is_open(self, value):
        self._is_open = value

    @property
    def location(self):
        return self._location

    @location.setter
    def location(self, value):
        self._location = value

    @property
    def name(self):
        return self._name

    @name.setter
    def name(self, value):
        self._name = value

    @property
    def course_code(self):
        return self._course_code

    @course_code.setter
    def course_code(self, value):
        self._course_code = value

    @property
    def open_seats(self):
        return self._open_seats

    @open_seats.setter
    def open_seats(self, value):
        self._open_seats = value

    @property
    def section(self):
        return self._section

    @section.setter
    def section(self, value):
        self._section = value

    @property
    def semester(self):
        return self._semester

    @semester.setter
    def semester(self, value):
        self._semester = value

    @property
    def department(self):   
        return self._department

    @department.setter
    def department(self, value):
        self._department = value

    @property
    def times(self):
        return self._times

    def add_time(self, meeting_time):
        self._times.append(meeting_time)

    @property
    def total_seats(self):
        return self._total_seats

    @total_seats.setter
    def total_seats(self, value):
        self._total_seats = value

    def __str__(self):
        return f"{self.name} - {self.department.upper()} {self.course_code}{self.section.upper()}"  

    def has_time_conflict(self, other_course: Course | None = None, other_courses: list[Course] | None = None) -> bool:
        # If other_course is None and other_courses is provided, check for conflicts with each course in other_courses
        if other_course is None and other_courses is not None:
            for curr_course in other_courses:
                # Recursively check for time conflicts with each course in the list
                if self.has_time_conflict(other_course=curr_course):
                    return True
            return False
        # If other_course is provided and other_courses is None, check for conflicts with the given course
        elif other_course is not None and other_courses is None:
            for other_time in other_course.times:
                for time in self.times:
                    # Check if the days match
                    if time.day == other_time.day:
                        start1 = time.start_time
                        end1 = time.end_time
                        start2 = other_time.start_time
                        end2 = other_time.end_time

                        # Check for overlapping time intervals
                        if ((start1 <= start2 <= end1) or
                            (start1 <= end2 <= end1) or
                            (start2 <= start1 <= end2)):
                            return True
            return False
        # Raise an error if both or neither of other_course and other_courses are provided
        else:
            raise ValueError("Only one of other_course and other_courses must be provided.")

def courses_have_time_conflict(courses: Iterable[Course], course: Course) -> bool:
    for curr_course in courses:
        if curr_course.has_time_conflict(course):
            return True
    return False
