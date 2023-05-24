package blanco.restgeneratorkt;

import java.io.File;

import blanco.restgeneratorkt.valueobject.BlancoRestGeneratorKtTelegramProcessStructure;

/**
 * Source file Expander interface
 */
public interface BlancoRestGeneratorKtExpanderInterface {
    /**
     * Expand source files
     * @param argProcessStructure
     * @param argDirectoryTarget
     */
    public void expand(
        final BlancoRestGeneratorKtTelegramProcessStructure argProcessStructure,
        final File argDirectoryTarget
    );
}
