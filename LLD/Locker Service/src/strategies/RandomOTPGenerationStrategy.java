package strategies;

public class RandomOTPGenerationStrategy implements IOTPGenerationStrategy {
    public String generateOtp() {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int random = (int) (Math.random() * 9) + 1;
            otp.append(random);
        }
        return otp.toString();
    }
}
