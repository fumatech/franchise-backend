package com.franchise.ServiceImpl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.franchise.Entity.Attendance;
import com.franchise.Entity.AttendanceBulkRequest;
import com.franchise.Entity.AttendanceRequest;
import com.franchise.Entity.AttendanceStatus;
import com.franchise.Entity.User;
import com.franchise.Repository.AttendanceRepo;
import com.franchise.Repository.UserRepo;
import com.franchise.Service.AttendanceService;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepo attendanceRepo;
    @Autowired
    private UserRepo userRepo;

    @Override
    public Attendance saveAttendance(Attendance attendance) {
        attendance.setStatus(calculateStatus(attendance.getInTime(), attendance.getOutTime()));
        attendance.setTotalWorkingHours(calculateWorkingHours(attendance.getInTime(), attendance.getOutTime()));
        return attendanceRepo.save(attendance);
    }

    @Override
    public void saveBulkAttendance(AttendanceBulkRequest request) {
        List<Attendance> list = new ArrayList<>();
        for (AttendanceRequest r : request.getRecords()) {
            Attendance a = new Attendance();
            a.setEmployeeId(r.getEmployeeId());
            a.setShiftId(r.getShiftId());
            a.setAttendanceDate(request.getAttendanceDate());
            a.setInTime(r.getInTime());
            a.setOutTime(r.getOutTime());
            a.setIpAddress(r.getIpAddress());
            a.setInNote(r.getInNote());
            a.setOutNote(r.getOutNote());
            a.setStatus(calculateStatus(r.getInTime(), r.getOutTime()));
            a.setTotalWorkingHours(calculateWorkingHours(r.getInTime(), r.getOutTime()));
            list.add(a);
        }
        attendanceRepo.saveAll(list);
    }

    @Override
    public List<Attendance> getAllAttendance() {
        return attendanceRepo.findAll();
    }

    @Override
    public Attendance getAttendanceById(Long id) {
        return attendanceRepo.findById(id).orElseThrow(() -> new RuntimeException("Attendance not found"));
    }

    @Override
    public void deleteAttendance(Long id) {
        attendanceRepo.deleteById(id);
    }

    @Override
    public void deleteAllAttendance() {
        attendanceRepo.deleteAll();
    }

    private AttendanceStatus calculateStatus(LocalDateTime inTime, LocalDateTime outTime) {
        if (inTime == null && outTime == null) {
            return AttendanceStatus.ABSENT;
        }
        if (inTime != null && outTime == null) {
            return AttendanceStatus.HALF_DAY;
        }
        return AttendanceStatus.PRESENT;
    }

    private Double calculateWorkingHours(LocalDateTime inTime, LocalDateTime outTime) {
        if (inTime == null || outTime == null) {
            return 0.0;
        }
        Duration duration = Duration.between(inTime, outTime);
        return Math.round(duration.toMinutes() / 6.0) / 10.0;
    }

    @Override
    public Attendance updateAttendance(Long id, Attendance updatedAttendance) {
        Attendance existing = attendanceRepo.findById(id).orElseThrow(() -> new RuntimeException("Attendance not found"));
        existing.setEmployeeId(updatedAttendance.getEmployeeId());
        existing.setShiftId(updatedAttendance.getShiftId());
        existing.setAttendanceDate(updatedAttendance.getAttendanceDate());
        existing.setInTime(updatedAttendance.getInTime());
        existing.setOutTime(updatedAttendance.getOutTime());
        existing.setIpAddress(updatedAttendance.getIpAddress());
        existing.setInNote(updatedAttendance.getInNote());
        existing.setOutNote(updatedAttendance.getOutNote());
        existing.setStatus(calculateStatus(updatedAttendance.getInTime(), updatedAttendance.getOutTime()));
        existing.setTotalWorkingHours(calculateWorkingHours(updatedAttendance.getInTime(), updatedAttendance.getOutTime()));
        if (updatedAttendance.getLoginMethod() != null) {
            existing.setLoginMethod(updatedAttendance.getLoginMethod());
        }
        return attendanceRepo.save(existing);
    }

    @Override
    public Attendance getTodayAttendance(Long employeeId) {
        LocalDate today = LocalDate.now();
        List<Attendance> records = attendanceRepo.findByEmployeeIdAndAttendanceDate(employeeId, today);
        if (records.isEmpty()) {
            return null;
        }
        for (int i = records.size() - 1; i >= 0; i--) {
            Attendance a = records.get(i);
            if (a.getInTime() != null && a.getOutTime() == null) {
                return a;
            }
        }
        return records.get(records.size() - 1);
    }

    @Override
    public List<Attendance> getMonthlyAttendance(Long employeeId, int month, int year) {
        YearMonth ym = YearMonth.of(year, month);
        LocalDate startDate = ym.atDay(1);
        LocalDate endDate = ym.atEndOfMonth();
        return attendanceRepo.findByEmployeeIdAndAttendanceDateBetween(employeeId, startDate, endDate);
    }

    @Override
    public Map<String, Object> getMonthlyWorkingSummary(Long employeeId, int month, int year) {
        List<Attendance> records = getMonthlyAttendance(employeeId, month, year);
        double totalHours = 0.0;
        Set<LocalDate> presentDates = new HashSet<>();
        Set<LocalDate> absentDates = new HashSet<>();
        Set<LocalDate> halfDayDates = new HashSet<>();
        for (Attendance a : records) {
            if (a.getTotalWorkingHours() != null) {
                totalHours += a.getTotalWorkingHours();
            }
            LocalDate date = a.getAttendanceDate();
            switch (a.getStatus()) {
                case PRESENT ->
                    presentDates.add(date);
                case ABSENT ->
                    absentDates.add(date);
                case HALF_DAY ->
                    halfDayDates.add(date);
            }
        }
        absentDates.removeAll(presentDates);
        halfDayDates.removeAll(presentDates);
        long presentDays = presentDates.size();
        long absentDays = absentDates.size();
        long halfDays = halfDayDates.size();
        User employee = userRepo.findById(employeeId).orElse(null);
        Map<String, Object> summary = new HashMap<>();
        summary.put("employeeId", employeeId);
        summary.put("month", month);
        summary.put("year", year);
        summary.put("totalWorkingHours", Math.round(totalHours * 100.0) / 100.0);
        summary.put("presentDays", presentDays);
        summary.put("absentDays", absentDays);
        summary.put("halfDays", halfDays);
        summary.put("totalRecords", records.size());
        if (employee != null) {
            summary.put("employeeName", employee.getFirstname() + " " + employee.getLastname());
            summary.put("employeeType", employee.getEmployeeType());
            summary.put("basicSalary", employee.getBasicSalary());
            summary.put("hourlyRate", employee.getHourlyRate());
            String employeeType = employee.getEmployeeType();
            if ("HOURLY".equals(employeeType) || "FREELANCER".equals(employeeType)) {
                if (employee.getHourlyRate() != null) {
                    double payable = totalHours * employee.getHourlyRate().doubleValue();
                    summary.put("calculatedPay", Math.round(payable * 100.0) / 100.0);
                }
            } else {
                summary.put("calculatedPay", employee.getBasicSalary());
            }
        }
        return summary;
    }

    @Override
    public List<Map<String, Object>> getAllEmployeesMonthlyWorkingSummary(int month, int year) {
        YearMonth ym = YearMonth.of(year, month);
        LocalDate startDate = ym.atDay(1);
        LocalDate endDate = ym.atEndOfMonth();
        List<Attendance> allRecords = attendanceRepo.findByAttendanceDateBetween(startDate, endDate);
        Map<Long, List<Attendance>> grouped = new HashMap<>();
        for (Attendance a : allRecords) {
            grouped.computeIfAbsent(a.getEmployeeId(), k -> new ArrayList<>()).add(a);
        }
        List<User> allUsers = userRepo.findAllById(grouped.keySet());
        Map<Long, User> userMap = new HashMap<>();
        for (User u : allUsers) {
            userMap.put(u.getId(), u);
        }
        List<Map<String, Object>> summaries = new ArrayList<>();
        for (Map.Entry<Long, List<Attendance>> entry : grouped.entrySet()) {
            Long employeeId = entry.getKey();
            List<Attendance> records = entry.getValue();
            double totalHours = 0.0;
            Set<LocalDate> presentDates = new HashSet<>();
            Set<LocalDate> absentDates = new HashSet<>();
            Set<LocalDate> halfDayDates = new HashSet<>();
            for (Attendance a : records) {
                if (a.getTotalWorkingHours() != null) {
                    totalHours += a.getTotalWorkingHours();
                }
                LocalDate date = a.getAttendanceDate();
                switch (a.getStatus()) {
                    case PRESENT ->
                        presentDates.add(date);
                    case ABSENT ->
                        absentDates.add(date);
                    case HALF_DAY ->
                        halfDayDates.add(date);
                }
            }
            absentDates.removeAll(presentDates);
            halfDayDates.removeAll(presentDates);
            long presentDays = presentDates.size();
            long absentDays = absentDates.size();
            long halfDays = halfDayDates.size();
            User employee = userMap.get(employeeId);
            Map<String, Object> summary = new HashMap<>();
            summary.put("employeeId", employeeId);
            summary.put("month", month);
            summary.put("year", year);
            summary.put("totalWorkingHours", Math.round(totalHours * 100.0) / 100.0);
            summary.put("presentDays", presentDays);
            summary.put("absentDays", absentDays);
            summary.put("halfDays", halfDays);
            summary.put("totalRecords", records.size());
            if (employee != null) {
                summary.put("employeeName", employee.getFirstname() + " " + employee.getLastname());
                summary.put("employeeType", employee.getEmployeeType());
                summary.put("basicSalary", employee.getBasicSalary());
                summary.put("hourlyRate", employee.getHourlyRate());
                String employeeType = employee.getEmployeeType();
                if ("HOURLY".equals(employeeType) || "FREELANCER".equals(employeeType)) {
                    if (employee.getHourlyRate() != null) {
                        double payable = totalHours * employee.getHourlyRate().doubleValue();
                        summary.put("calculatedPay", Math.round(payable * 100.0) / 100.0);
                    }
                } else {
                    summary.put("calculatedPay", employee.getBasicSalary());
                }
            }
            summaries.add(summary);
        }
        return summaries;
    }

    @Override
    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceRepo.findByAttendanceDate(date);
    }

    @Override
    public List<Attendance> getTodayFaceScans() {
        return attendanceRepo.findByAttendanceDateAndLoginMethod(LocalDate.now(), "FACE_SCAN");
    }
}
