from datetime import time

class MeetingTime:
    def __init__(self, start_time, end_time, day):
        self.start_time = start_time
        self.end_time = end_time
        self.day = day

class Course:
    def __init__(self, id):
        self.id = id
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
        self._subject = ""
        self._times = []
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
    def subject(self):
        return self._subject

    @subject.setter
    def subject(self, value):
        self._subject = value

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
        return f"{self.name} - {self.subject.upper()} {self.course_code}{self.section.upper()}"
