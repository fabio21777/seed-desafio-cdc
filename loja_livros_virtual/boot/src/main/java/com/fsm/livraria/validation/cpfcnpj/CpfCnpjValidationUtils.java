package com.fsm.livraria.validation.cpfcnpj;

/**
 * Utility methods to ease {@link CPFOrCNPJ} validation.
 */
public final class CpfCnpjValidationUtils {

    private CpfCnpjValidationUtils() {
    }

    /**
     * @param cpfOrCnpj the cpfOrCnpj to check
     * @return Whether the cpfOrCnpj is unique in the database (excluding the current entity)
     */
    public static boolean isValid(String cpfOrCnpj) {
        if (cpfOrCnpj == null || cpfOrCnpj.isEmpty()) {
            return true; // Valores vazios serão tratados por @NotBlank ou outros validadores
        }

        // Remove quaisquer caracteres não numéricos
        cpfOrCnpj = cpfOrCnpj.replaceAll("\\D", "");

        // Verifica se é CPF ou CNPJ com base no tamanho
        if (cpfOrCnpj.length() == 11) {
            return isValidCPF(cpfOrCnpj);
        } else if (cpfOrCnpj.length() == 14) {
            return isValidCNPJ(cpfOrCnpj);
        }

        return false; // Tamanho inválido
    }

    private static boolean isValidCPF(String cpf) {
        // Verifica dígitos repetidos (casos inválidos de CPF)
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        int[] digitos = new int[11];
        for (int i = 0; i < 11; i++) {
            digitos[i] = Character.getNumericValue(cpf.charAt(i));
        }

        // Cálculo do primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += digitos[i] * (10 - i);
        }

        int resto = soma % 11;
        int digitoVerificador1 = (resto < 2) ? 0 : (11 - resto);

        if (digitoVerificador1 != digitos[9]) {
            return false;
        }

        // Cálculo do segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += digitos[i] * (11 - i);
        }

        resto = soma % 11;
        int digitoVerificador2 = (resto < 2) ? 0 : (11 - resto);

        return digitoVerificador2 == digitos[10];
    }

    private static boolean isValidCNPJ(String cnpj) {
        // Verifica dígitos repetidos (casos inválidos de CNPJ)
        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        // Cálculo do primeiro dígito verificador
        int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int soma = 0;

        for (int i = 0; i < 12; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * pesos1[i];
        }

        int resto = soma % 11;
        int digitoVerificador1 = (resto < 2) ? 0 : (11 - resto);

        if (digitoVerificador1 != Character.getNumericValue(cnpj.charAt(12))) {
            return false;
        }

        // Cálculo do segundo dígito verificador
        int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        soma = 0;

        for (int i = 0; i < 13; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * pesos2[i];
        }

        resto = soma % 11;
        int digitoVerificador2 = (resto < 2) ? 0 : (11 - resto);

        return digitoVerificador2 == Character.getNumericValue(cnpj.charAt(13));
    }
}