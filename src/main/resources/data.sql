INSERT INTO departments (department_id, name, description, floor_number, contact_number, status, created_at, updated_at, version) VALUES
(1, 'Cardiology', 'Diagnosis and treatment of heart disorders', 2, '+91-11-2345-0001', 'ACTIVE', NOW(), NOW(), 0),
(2, 'Neurology', 'Diagnosis and treatment of nervous system disorders', 3, '+91-11-2345-0002', 'ACTIVE', NOW(), NOW(), 0),
(3, 'Orthopedics', 'Diagnosis and treatment of musculoskeletal system', 4, '+91-11-2345-0003', 'ACTIVE', NOW(), NOW(), 0),
(4, 'Pediatrics', 'Medical care for infants, children and adolescents', 1, '+91-11-2345-0004', 'ACTIVE', NOW(), NOW(), 0),
(5, 'Oncology', 'Diagnosis and treatment of cancer', 5, '+91-11-2345-0005', 'ACTIVE', NOW(), NOW(), 0),
(6, 'Emergency Medicine', 'Immediate treatment for acute illnesses and injuries', 0, '+91-11-2345-0006', 'ACTIVE', NOW(), NOW(), 0),
(7, 'Dermatology', 'Diagnosis and treatment of skin disorders', 2, '+91-11-2345-0007', 'ACTIVE', NOW(), NOW(), 0),
(8, 'Psychiatry', 'Diagnosis and treatment of mental health disorders', 6, '+91-11-2345-0008', 'ACTIVE', NOW(), NOW(), 0);

INSERT INTO specialties (specialty_id, name, description, created_at, updated_at, version) VALUES
(1, 'Interventional Cardiology', 'Catheter-based techniques to treat cardiovascular diseases', NOW(), NOW(), 0),
(2, 'Electrophysiology', 'Diagnosis and treatment of heart rhythm disorders', NOW(), NOW(), 0),
(3, 'Stroke Medicine', 'Specialized treatment for stroke and cerebrovascular conditions', NOW(), NOW(), 0),
(4, 'Epilepsy', 'Diagnosis and management of seizure disorders', NOW(), NOW(), 0),
(5, 'Joint Replacement', 'Surgical replacement of damaged joints', NOW(), NOW(), 0),
(6, 'Spine Surgery', 'Surgical treatment of spinal disorders', NOW(), NOW(), 0),
(7, 'Neonatal Care', 'Specialized care for newborn infants', NOW(), NOW(), 0),
(8, 'Pediatric Oncology', 'Cancer treatment specifically for children', NOW(), NOW(), 0),
(9, 'Medical Oncology', 'Non-surgical cancer treatment including chemotherapy', NOW(), NOW(), 0),
(10, 'Trauma Surgery', 'Emergency surgical care for traumatic injuries', NOW(), NOW(), 0),
(11, 'Cosmetic Dermatology', 'Aesthetic and cosmetic skin treatments', NOW(), NOW(), 0),
(12, 'Child Psychiatry', 'Mental health treatment for children and adolescents', NOW(), NOW(), 0);

INSERT INTO insurances (insurance_id, provider_name, policy_number, plan_type, coverage_amount, premium_amount, valid_from, valid_until, active, created_at, updated_at, version) VALUES
(1, 'Star Health Insurance', 'STAR-2024-001', 'PREMIUM', 500000.00, 12000.00, '2024-01-01', '2026-12-31', true, NOW(), NOW(), 0),
(2, 'HDFC ERGO Health', 'HDFC-2024-002', 'STANDARD', 300000.00, 8000.00, '2024-03-01', '2026-02-28', true, NOW(), NOW(), 0),
(3, 'Ayushman Bharat', 'AYUSH-2024-003', 'GOVERNMENT', 500000.00, 0.00, '2024-01-01', '2026-12-31', true, NOW(), NOW(), 0),
(4, 'Reliance Health', 'RELI-2024-004', 'BASIC', 150000.00, 4500.00, '2024-06-01', '2025-05-31', true, NOW(), NOW(), 0),
(5, 'ICICI Lombard', 'ICICI-2024-005', 'CORPORATE', 1000000.00, 25000.00, '2024-01-01', '2026-12-31', true, NOW(), NOW(), 0);

INSERT INTO medical_records (medical_record_id, blood_group, allergies, chronic_conditions, current_medications, past_surgeries, family_medical_history, height_cm, weight_kg, notes, created_at, updated_at, version) VALUES
    (1, 'O_POSITIVE', 'Penicillin, Sulfa drugs', 'Hypertension, Type 2 Diabetes', 'Metformin 500mg, Amlodipine 5mg', 'Appendectomy 2015', 'Father: Hypertension, Mother: Diabetes', 172.0, 78.5, 'Patient is compliant with medication', NOW(), NOW(), 0),
    (2, 'A_POSITIVE', 'None known', 'Asthma', 'Salbutamol Inhaler PRN', 'None', 'Mother: Asthma', 165.0, 62.0, 'Well-controlled asthma', NOW(), NOW(), 0),
    (3, 'B_NEGATIVE', 'Aspirin, NSAIDs', 'Rheumatoid Arthritis', 'Methotrexate 10mg weekly, Folic Acid', 'None', 'Sister: Rheumatoid Arthritis', 158.0, 55.0, 'Moderate disease activity', NOW(), NOW(), 0),
    (4, 'AB_POSITIVE', 'Latex', 'None', 'None', 'Tonsillectomy 2010, ACL Repair 2019', 'Father: Cardiac disease', 180.0, 82.0, 'Fit and active, annual checkup', NOW(), NOW(), 0),
    (5, 'O_NEGATIVE', 'Shellfish, Codeine', 'Depression, Anxiety', 'Sertraline 50mg, Clonazepam 0.5mg', 'None', 'Mother: Depression', 160.0, 68.0, 'Under psychiatric care', NOW(), NOW(), 0),
    (6, 'A_NEGATIVE', 'Pollen, Dust mites', 'Allergic Rhinitis', 'Cetirizine 10mg', 'None', 'Father: Allergies', 175.0, 70.0, 'Seasonal allergy management', NOW(), NOW(), 0),
    (7, 'B_POSITIVE', 'None known', 'Chronic Kidney Disease Stage 2', 'Losartan 50mg, Iron supplements', 'None', 'Father: Kidney disease', 168.0, 74.0, 'Regular nephrology follow-up required', NOW(), NOW(), 0),
    (8, 'AB_NEGATIVE', 'Ibuprofen', 'Hyperthyroidism', 'Carbimazole 10mg', 'None', 'Mother: Thyroid disorder', 155.0, 52.0, 'Thyroid function monitoring every 3 months', NOW(), NOW(), 0);

INSERT INTO privileges (privilege_id, name) VALUES
(1, 'VIEW_OWN_RECORD'),
(2, 'BOOK_APPOINTMENT'),
(3, 'CANCEL_APPOINTMENT'),
(4, 'VIEW_PATIENT_RECORD'),
(5, 'UPDATE_PATIENT_RECORD'),
(6, 'VIEW_SCHEDULE'),
(7, 'MANAGE_USERS'),
(8, 'MANAGE_DEPARTMENTS'),
(9, 'DELETE_ANY_APPOINTMENT');

INSERT INTO roles (role_id, name) VALUES
(1, 'ROLE_PATIENT'),
(2, 'ROLE_DOCTOR'),
(3, 'ROLE_ADMIN'),
(4, 'ROLE_RECEPTIONIST'),
(5, 'ROLE_NURSE'),
(6, 'ROLE_PHARMACIST');

INSERT INTO role_privileges (role_id, privilege_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 4),
(2, 5),
(2, 6),
(3, 7),
(3, 8),
(3, 9),
(4, 2),
(4, 4),
(4, 7),
(5, 4),
(5, 6);

INSERT INTO system_users (user_id, full_name, email, password, auth_provider, enabled, account_non_locked, linked_reference_id, created_at, updated_at, version) VALUES
(1, 'Dr. Rajesh Sharma', 'rajesh.sharma@hms.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TiGX/H.o7fL9C4eFTr.HwMvPpXHi', 'LOCAL', true, true, 1, NOW(), NOW(), 0),
(2, 'Dr. Priya Nair', 'priya.nair@hms.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TiGX/H.o7fL9C4eFTr.HwMvPpXHi', 'LOCAL', true, true, 2, NOW(), NOW(), 0),
(3, 'Dr. Amit Verma', 'amit.verma@hms.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TiGX/H.o7fL9C4eFTr.HwMvPpXHi', 'LOCAL', true, true, 3, NOW(), NOW(), 0),
(4, 'Dr. Sunita Patel', 'sunita.patel@hms.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TiGX/H.o7fL9C4eFTr.HwMvPpXHi', 'LOCAL', true, true, 4, NOW(), NOW(), 0),
(5, 'Dr. Karan Mehta', 'karan.mehta@hms.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TiGX/H.o7fL9C4eFTr.HwMvPpXHi', 'LOCAL', true, true, 5, NOW(), NOW(), 0),
(6, 'Rahul Gupta', 'rahul.gupta@hms.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TiGX/H.o7fL9C4eFTr.HwMvPpXHi', 'LOCAL', true, true, 1, NOW(), NOW(), 0),
(7, 'Anjali Singh', 'anjali.singh@hms.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TiGX/H.o7fL9C4eFTr.HwMvPpXHi', 'LOCAL', true, true, 2, NOW(), NOW(), 0),
(8, 'Vikram Joshi', 'vikram.joshi@hms.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TiGX/H.o7fL9C4eFTr.HwMvPpXHi', 'LOCAL', true, true, 3, NOW(), NOW(), 0),
(9, 'Admin User', 'admin@hms.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 'LOCAL', true, true, NULL, NOW(), NOW(), 0),
(10, 'Reception Staff', 'reception@hms.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 'LOCAL', true, true, NULL, NOW(), NOW(), 0);

INSERT INTO user_roles (user_id, role_id) VALUES
(1, 2),
(2, 2),
(3, 2),
(4, 2),
(5, 2),
(6, 1),
(7, 1),
(8, 1),
(9, 3),
(10, 4);

INSERT INTO doctors (doctor_id, first_name, last_name, email, phone_number, gender, date_of_birth, license_number, years_of_experience, consultation_fee, availability_status, department_id, created_at, updated_at, version) VALUES
(1, 'Rajesh', 'Sharma', 'rajesh.sharma@hms.com', '+91-9876543201', 'MALE', '1978-04-15', 'MCI-DL-2003-001', 22, 1500.00, 'AVAILABLE', 1, NOW(), NOW(), 0),
(2, 'Priya', 'Nair', 'priya.nair@hms.com', '+91-9876543202', 'FEMALE', '1983-09-22', 'MCI-DL-2008-002', 16, 1200.00, 'AVAILABLE', 2, NOW(), NOW(), 0),
(3, 'Amit', 'Verma', 'amit.verma@hms.com', '+91-9876543203', 'MALE', '1980-12-10', 'MCI-DL-2005-003', 19, 1800.00, 'AVAILABLE', 3, NOW(), NOW(), 0),
(4, 'Sunita', 'Patel', 'sunita.patel@hms.com', '+91-9876543204', 'FEMALE', '1985-06-30', 'MCI-DL-2010-004', 14, 1000.00, 'AVAILABLE', 4, NOW(), NOW(), 0),
(5, 'Karan', 'Mehta', 'karan.mehta@hms.com', '+91-9876543205', 'MALE', '1975-02-18', 'MCI-DL-2000-005', 25, 2000.00, 'AVAILABLE', 5, NOW(), NOW(), 0);

INSERT INTO doctor_specialties (doctor_id, specialty_id) VALUES
(1, 1), (1, 2),
(2, 3), (2, 4),
(3, 5), (3, 6),
(4, 7), (4, 8),
(5, 9), (5, 8);

INSERT INTO patients (patient_id, first_name, last_name, email, phone_number, gender, date_of_birth, address, emergency_contact_name, emergency_contact_number, status, medical_record_id, insurance_id, created_at, updated_at, version) VALUES
(1, 'Rahul', 'Gupta', 'rahul.gupta@hms.com', '+91-9812345601', 'MALE', '1985-07-20', '12 MG Road, Bangalore, Karnataka 560001', 'Meena Gupta', '+91-9812345700', 'ACTIVE', 1, 1, NOW(), NOW(), 0),
(2, 'Anjali', 'Singh', 'anjali.singh@hms.com', '+91-9812345602', 'FEMALE', '1992-03-14', '45 Park Street, Kolkata, West Bengal 700016', 'Ravi Singh', '+91-9812345701', 'ACTIVE', 2, 2, NOW(), NOW(), 0),
(3, 'Vikram', 'Joshi', 'vikram.joshi@hms.com', '+91-9812345603', 'MALE', '1975-11-05', '78 Connaught Place, New Delhi 110001', 'Kavita Joshi', '+91-9812345702', 'ACTIVE', 3, 3, NOW(), NOW(), 0),
(4, 'Neha', 'Kapoor', 'neha.kapoor@hms.com', '+91-9812345604', 'FEMALE', '1990-08-25', '23 Bandra West, Mumbai, Maharashtra 400050', 'Sanjay Kapoor', '+91-9812345703', 'ACTIVE', 4, 4, NOW(), NOW(), 0),
(5, 'Suresh', 'Kumar', 'suresh.kumar@hms.com', '+91-9812345605', 'MALE', '1988-01-30', '56 Anna Salai, Chennai, Tamil Nadu 600002', 'Lakshmi Kumar', '+91-9812345704', 'ACTIVE', 5, 2, NOW(), NOW(), 0),
(6, 'Pooja', 'Mehta', 'pooja.mehta@hms.com', '+91-9812345606', 'FEMALE', '1995-05-12', '34 CG Road, Ahmedabad, Gujarat 380009', 'Deepak Mehta', '+91-9812345705', 'ACTIVE', 6, 5, NOW(), NOW(), 0),
(7, 'Arjun', 'Reddy', 'arjun.reddy@hms.com', '+91-9812345607', 'MALE', '1982-09-18', '67 Banjara Hills, Hyderabad, Telangana 500034', 'Sridevi Reddy', '+91-9812345706', 'ACTIVE', 7, 1, NOW(), NOW(), 0),
(8, 'Kavya', 'Nair', 'kavya.nair@hms.com', '+91-9812345608', 'FEMALE', '1998-12-03', '89 MG Road, Kochi, Kerala 682011', 'Mohan Nair', '+91-9812345707', 'ACTIVE', 8, 3, NOW(), NOW(), 0);

INSERT INTO appointments (appointment_id, patient_id, doctor_id, scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, status, reason_for_visit, consultation_fee_charged, cancellation_reason, cancelled_by, notes, created_at, updated_at, version) VALUES
(1, 1, 1, '2026-07-10 09:00:00', '2026-07-10 09:30:00', NULL, NULL, 'SCHEDULED', 'Chest pain and shortness of breath', 1500.00, NULL, NULL, 'Patient reports symptoms for past 3 days', NOW(), NOW(), 0),
(2, 2, 2, '2026-07-10 10:00:00', '2026-07-10 10:30:00', NULL, NULL, 'SCHEDULED', 'Severe headaches and dizziness', 1200.00, NULL, NULL, 'Recurring migraines for 2 weeks', NOW(), NOW(), 0),
(3, 3, 3, '2026-07-11 11:00:00', '2026-07-11 11:45:00', NULL, NULL, 'CONFIRMED', 'Knee pain and swelling', 1800.00, NULL, NULL, 'Sports injury from last month', NOW(), NOW(), 0),
(4, 4, 4, '2026-07-09 09:00:00', '2026-07-09 09:30:00', '2026-07-09 09:05:00', '2026-07-09 09:35:00', 'COMPLETED', 'Child annual health checkup', 1000.00, NULL, NULL, 'All vitals normal, growth on track', NOW(), NOW(), 0),
(5, 5, 5, '2026-07-09 14:00:00', '2026-07-09 14:30:00', '2026-07-09 14:10:00', '2026-07-09 14:45:00', 'COMPLETED', 'Follow-up after chemotherapy cycle 3', 2000.00, NULL, NULL, 'Responded well to treatment, next cycle in 3 weeks', NOW(), NOW(), 0),
(6, 6, 1, '2026-07-08 11:00:00', '2026-07-08 11:30:00', NULL, NULL, 'CANCELLED', 'Routine cardiac screening', 1500.00, 'Patient unable to attend due to personal emergency', 'PATIENT', NULL, NOW(), NOW(), 0),
(7, 7, 2, '2026-07-12 15:00:00', '2026-07-12 15:30:00', NULL, NULL, 'REQUESTED', 'Memory loss and concentration issues', 1200.00, NULL, NULL, NULL, NOW(), NOW(), 0),
(8, 8, 3, '2026-07-13 10:00:00', '2026-07-13 10:45:00', NULL, NULL, 'SCHEDULED', 'Lower back pain radiating to leg', 1800.00, NULL, NULL, 'Possible disc herniation based on symptoms', NOW(), NOW(), 0),
(9, 1, 5, '2026-07-15 09:00:00', '2026-07-15 09:30:00', NULL, NULL, 'SCHEDULED', 'Second opinion on cardiac medication', 2000.00, NULL, NULL, NULL, NOW(), NOW(), 0),
(10, 3, 1, '2026-07-14 13:00:00', '2026-07-14 13:30:00', NULL, NULL, 'CONFIRMED', 'Pre-surgery cardiac clearance', 1500.00, NULL, NULL, 'Required before knee replacement surgery', NOW(), NOW(), 0);

SELECT setval('departments_department_id_seq', (SELECT MAX(department_id) FROM departments));
SELECT setval('specialties_specialty_id_seq', (SELECT MAX(specialty_id) FROM specialties));
SELECT setval('insurances_insurance_id_seq', (SELECT MAX(insurance_id) FROM insurances));
SELECT setval('medical_records_medical_record_id_seq', (SELECT MAX(medical_record_id) FROM medical_records));
SELECT setval('privileges_privilege_id_seq', (SELECT MAX(privilege_id) FROM privileges));
SELECT setval('roles_role_id_seq', (SELECT MAX(role_id) FROM roles));
SELECT setval('system_users_user_id_seq', (SELECT MAX(user_id) FROM system_users));
SELECT setval('doctors_doctor_id_seq', (SELECT MAX(doctor_id) FROM doctors));
SELECT setval('patients_patient_id_seq', (SELECT MAX(patient_id) FROM patients));
SELECT setval('appointments_appointment_id_seq', (SELECT MAX(appointment_id) FROM appointments));