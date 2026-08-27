#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
DOC = ROOT / "docs/glaze-motion-evaluation.md"
ADOPTION = ROOT / "docs/glaze-ui-adoption.md"
TEST = ROOT / "android/app/src/androidTest/kotlin/com/goreecloud/keyboard/GlazeMotionExperimentalKeyboardRuntimeTest.kt"
MAIN = ROOT / "android/app/src/main"
KEYBOARD_VIEW = MAIN / "kotlin/com/goreecloud/keyboard/KeyboardView.kt"
REFERENCE_REVISION = "b386c793c047e2f5d5d92125732f142e7fdf32dc"
STABLE_REVISION = "2e1618397f6ebcdd254a76bfdd7e98846f2c5aa3"
MARKER = "GlazeMotionExperimental"


def fail(message: str) -> None:
    raise SystemExit(f"Glaze Motion keyboard evaluation failed: {message}")


def main() -> None:
    for path in (DOC, ADOPTION, TEST, KEYBOARD_VIEW):
        if not path.is_file():
            fail(f"missing required evidence: {path.relative_to(ROOT)}")

    doc_text = DOC.read_text(encoding="utf-8")
    adoption_text = ADOPTION.read_text(encoding="utf-8")
    test_text = TEST.read_text(encoding="utf-8")
    view_text = KEYBOARD_VIEW.read_text(encoding="utf-8")

    required_doc = [
        "Lifecycle: **Experimental 0.5**",
        f"Reviewed canonical revision: `{REFERENCE_REVISION}`",
        "Runtime compatibility baseline: **0.4.0**",
        "Evaluation mode: **native Android interaction mapping, test-only**",
        "Production dependency: **no**",
        "insufficient for Candidate promotion by itself",
    ]
    for evidence in required_doc:
        if evidence not in doc_text:
            fail(f"missing lifecycle or evidence boundary `{evidence}`")

    required_adoption = [
        "Status: **Adoption Candidate**",
        "Required Stable baseline: **Glaze UI 1.5.0**",
        f"Reviewed canonical Stable revision: `{STABLE_REVISION}`",
        "Production eligible on the Glaze UI gate: **no**",
        "Experimental Glaze Motion is not a production dependency.",
    ]
    for evidence in required_adoption:
        if evidence not in adoption_text:
            fail(f"missing Glaze UI adoption boundary `{evidence}`")

    required_test = [
        f'const val REFERENCE_REVISION = "{REFERENCE_REVISION}"',
        'const val VERSION = "0.5.0"',
        'const val RUNTIME_BASELINE = "0.4.0"',
        "Settings.Global.ANIMATOR_DURATION_SCALE",
        "allowsOptionalSettling(",
        "KeyboardView(context)",
        "dispatchTouchEvent(event)",
    ]
    for evidence in required_test:
        if evidence not in test_text:
            fail(f"missing native test-only evidence `{evidence}`")

    required_consumer = [
        "class KeyboardView",
        "override fun onTouchEvent(event: MotionEvent)",
        "listener?.onSuggestion(hit.value)",
        "Action.CHARACTER -> listener?.onCharacter",
        "performClick()",
    ]
    for evidence in required_consumer:
        if evidence not in view_text:
            fail(f"representative Keyboard interaction surface no longer exposes `{evidence}`")

    production_hits = []
    for path in MAIN.rglob("*.kt"):
        if MARKER in path.read_text(encoding="utf-8"):
            production_hits.append(str(path.relative_to(ROOT)))
    if production_hits:
        fail(
            "Experimental mapping escaped test quarantine into production source: "
            + ", ".join(production_hits)
        )

    print(
        "Glaze Motion 0.5 Keyboard test-only evaluation boundary passed: "
        "native interaction surface mapped, production source remains quarantined."
    )


if __name__ == "__main__":
    main()
