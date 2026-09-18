<?php
declare(strict_types=1);

$allowedOrigins = [
    'http://localhost:18081',
    'http://127.0.0.1:18081',
];
$origin = $_SERVER['HTTP_ORIGIN'] ?? '';

if (in_array($origin, $allowedOrigins, true)) {
    header('Access-Control-Allow-Origin: ' . $origin);
    header('Vary: Origin');
}

header('Content-Type: application/json; charset=UTF-8');

if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    header('Access-Control-Allow-Methods: POST, OPTIONS');
    header('Access-Control-Allow-Headers: Content-Type');
    http_response_code(204);
    exit;
}

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    http_response_code(405);
    echo json_encode([
        'valid' => false,
        'message' => 'Use a POST request to validate registration details.',
    ]);
    exit;
}

$name = trim((string) ($_POST['name'] ?? ''));
$email = trim((string) ($_POST['email'] ?? ''));
$password = (string) ($_POST['password'] ?? '');
$confirmPassword = (string) ($_POST['confirmPassword'] ?? '');
$message = '';

if (strlen($name) < 2) {
    $message = 'Enter a name with at least 2 characters.';
} elseif (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
    $message = 'Enter a valid email address.';
} elseif (strlen($password) < 6) {
    $message = 'Password must contain at least 6 characters.';
} elseif (!hash_equals($password, $confirmPassword)) {
    $message = 'Passwords do not match.';
}

if ($message !== '') {
    http_response_code(422);
    echo json_encode([
        'valid' => false,
        'message' => $message,
    ]);
    exit;
}

echo json_encode([
    'valid' => true,
    'message' => 'Registration details are valid.',
]);
