package gfb.php_storm.support.skeleton;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import org.jetbrains.annotations.NotNull;

/**
 * Created by scherk01 on 11.04.2016.
 */
public class ComponentPsiReferenceContributor extends PsiReferenceContributor {
    private static final Logger logger = Logger.getInstance(ComponentPsiReferenceContributor.class);

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar psiReferenceRegistrar) {
        logger.warn("Providers registered");

        ComponentPsiReferenceProvider provider = new ComponentPsiReferenceProvider();
        psiReferenceRegistrar.registerReferenceProvider(StandardPatterns.instanceOf(PsiElement.class), provider);
    }
}
