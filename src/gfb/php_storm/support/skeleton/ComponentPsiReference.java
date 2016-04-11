package gfb.php_storm.support.skeleton;

import com.intellij.codeInsight.completion.CompletionProcess;
import com.intellij.codeInsight.completion.CompletionService;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.util.ArrayUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scherk01 on 11.04.2016.
 */
public class ComponentPsiReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private static final Logger logger = Logger.getInstance(ComponentPsiReference.class);

    public ComponentPsiReference(@NotNull PsiElement element) {
        super(element);
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean b) {
        List<ResolveResult> results = new ArrayList<ResolveResult>();

        Project project = myElement.getProject();
        VirtualFile templateFile = project.getBaseDir().findFileByRelativePath("logs/test1.log");
        if (null != templateFile) {
            PsiFile psiFile = PsiManager.getInstance(project).findFile(templateFile);
            if (psiFile != null) {
                results.add(
                        new PsiElementResolveResult(psiFile)
                );
            }
        }

        return results.toArray(new ResolveResult[results.size()]);
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        final CompletionProcess process = CompletionService.getCompletionService().getCurrentCompletion();
        if (process != null && process.isAutopopupCompletion() && isSoft()) {
            return ArrayUtil.EMPTY_OBJECT_ARRAY;
        }

        List<Object> encodedVariants = new ArrayList<Object>();
        try {
            final PsiElement element = this.getElement();
            encodedVariants.add(element);
        } catch (Exception e) {
            logger.warn("Exception in getReferencesByElement - " + e.getMessage());
        }
        return ArrayUtil.toObjectArray(encodedVariants);
    }
}
